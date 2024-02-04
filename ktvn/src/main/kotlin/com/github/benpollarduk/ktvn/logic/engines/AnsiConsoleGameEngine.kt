package com.github.benpollarduk.ktvn.logic.engines

import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.audio.VolumeManager
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.ProgressionController
import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.ChapterTransition
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.Story
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import com.github.benpollarduk.ktvn.text.frames.CharacterConstrainedTextFrame
import com.github.benpollarduk.ktvn.text.frames.TextFrame
import com.github.benpollarduk.ktvn.text.frames.TextFrameParameters
import com.github.benpollarduk.ktvn.text.log.Log
import com.github.benpollarduk.ktvn.text.sequencing.GridTextSequencer
import com.github.benpollarduk.ktvn.text.sequencing.SequencedTextController
import com.github.benpollarduk.ktvn.text.sequencing.SequencedTextControllerListener
import java.util.concurrent.locks.ReentrantLock

/**
 * A class that functions as an engine for an ANSI compatible console. The display [parameters] can be specified to
 * set up the text frame. An [enterPrompt] can be specified, this will be displayed when user input is awaited.
 */
@Suppress("TooManyFunctions")
public class AnsiConsoleGameEngine(
    private val parameters: TextFrameParameters = TextFrameParameters(DEFAULT_WIDTH, DEFAULT_LINES),
    private val enterPrompt: String = "<enter> "
) : GameEngine {
    private val lock: ReentrantLock = ReentrantLock()
    private var isProcessingInput = false
    private var input = ""
    private var canSkipCurrentStep = false
    private var cancellationToken: CancellationToken = CancellationToken()

    // the text engine is responsible for sequencing and controlling the dispatch of characters from a collection
    // of frames. the listener is used to capture the requested characters and render them on the console
    private val textController = SequencedTextController(
        GridTextSequencer(SequencedTextController.DEFAULT_MS_BETWEEN_CHARACTERS) {
            // render all the characters in the requested position on the console
            for (position in it) {
                setCursorPosition(position.column + 1, position.row + 1)
                print(position.character)
            }
        }
    )

    override val log: Log = Log()
    override val progressionController: ProgressionController = ProgressionController()
    override val volumeManager: VolumeManager = VolumeManager()

    init {
        // set up the listener for when the textController has to split a text frame and requires acknowledgement to
        // continue
        textController.addListener(object : SequencedTextControllerListener {
            override fun startedFrame(frame: TextFrame) {
                // clear the console before rendering the new frame
                clear()

                // get the mode - if the mode is set to skip and the current step can be skipped, or skip unseen is on
                // then skip sequencing the frame
                val mode = progressionController.progressionMode
                if (mode is ProgressionMode.Skip && (canSkipCurrentStep || mode.skipUnseen)) {
                    textController.skip()
                }
            }

            override fun finishedFrame(frame: TextFrame) {
                // no handling
            }

            override fun waitFor() {
                // wait for enter to be pressed before continuing
                waitForAcknowledge(cancellationToken)
            }
        })

        // hide the cursor
        hideCursor()
    }

    /**
     * Set the cursor to a specified [x] and [y] position.
     */
    private fun setCursorPosition(x: Int, y: Int) {
        // use ANSI escape codes to set the cursor position
        kotlin.io.print("\u001b[$y;${x}H")
    }

    /**
     * Hide the cursor.
     */
    private fun hideCursor() {
        // use ANSI escape codes to hide the cursor
        kotlin.io.print("\u001b[?25l")
    }

    /**
     * Set the input value to a specified [input].
     */
    internal fun setInput(input: String) {
        try {
            lock.lock()
            this.input = input
        } finally {
            lock.unlock()
        }
    }

    /**
     * Wait for the enter key to be pressed or a signal to the auto time controller to begin. A cancellation token
     * must be provided to support cancellation.
     */
    private fun waitForAcknowledge(cancellationToken: CancellationToken) {
        setCursorPosition(parameters.widthConstraint + 1, parameters.availableLines + 1)
        kotlin.io.print(enterPrompt)
        progressionController.awaitAcknowledgement(canSkipCurrentStep, cancellationToken)
    }

    /**
     * Wait for entry followed by the enter key. A cancellation token must be provided to support cancellation.
     */
    private fun waitForInput(cancellationToken: CancellationToken): String {
        // put in manual mode
        progressionController.progressionMode = ProgressionMode.WaitForConfirmation

        // reset the input to ensure that legacy input is discarded
        setInput("")
        waitForAcknowledge(cancellationToken)

        return try {
            lock.lock()
            // return a clone of the received input
            String(input.toCharArray())
        } finally {
            lock.unlock()
        }
    }

    /**
     * Clear the console.
     */
    private fun clear() {
        // ANSI escape code to clear the screen
        kotlin.io.print("\u001b[H\u001b[2J")
        // flush output
        System.out.flush()
    }

    /**
     * Print a [string] to the console. Optionally an ANSI color code can be specified, otherwise 97 (white) will be
     * used.
     */
    private fun print(string: String, colorCode: Int = ANSI_WHITE) {
        // if color is suppressed ensure white is used
        val color = if (isColorSuppressed()) {
            ANSI_WHITE
        } else {
            colorCode
        }

        // set color
        kotlin.io.print("\u001B[${color}m")

        // create frames from the string that describe how it should be rendered
        val frames = CharacterConstrainedTextFrame.create(
            string,
            parameters
        )

        // render the frames on the console
        textController.render(frames)

        // reset color
        kotlin.io.print("\u001B[0m")
    }

    /**
     * Display a [string] directly on the console temporarily. The default 1000ms duration can be specified with
     * [durationInMs], in milliseconds. Optionally an ANSI color code can be specified, otherwise 90 (bright black)
     * will be used.
     */
    private fun printlnDirectTemp(string: String, durationInMs: Long = 1000, colorCode: Int = ANSI_BRIGHT_BLACK) {
        clear()

        // if color is suppressed ensure white is used
        val color = if (isColorSuppressed()) {
            ANSI_WHITE
        } else {
            colorCode
        }

        // print string wrapped in ANSI color setting to specified colour and then resetting to 0 (reset)
        println("\u001B[${color}m$string\u001B[0m")

        if (durationInMs > 0) {
            Thread.sleep(durationInMs)
        }
    }

    /**
     * Begin processing console input.
     */
    public fun beginProcessingInput() {
        isProcessingInput = true

        while (isProcessingInput) {
            val input = readln()

            if (textController.sequencing) {
                textController.skip()
            } else {
                setInput(input)
            }

            progressionController.triggerAcknowledgementLatch()
        }
    }

    /**
     * End processing of console input.
     */
    public fun endProcessingInput() {
        isProcessingInput = false
    }

    /**
     * Print a [line] and wait for an acknowledgement.
     */
    private fun printAndWaitForAcknowledge(line: String) {
        print(line)
        waitForAcknowledge(cancellationToken)
        clear()
    }

    override fun characterSpeaks(character: Character, line: String) {
        printAndWaitForAcknowledge("${character.name}: $line")
    }

    override fun characterThinks(character: Character, line: String) {
        printAndWaitForAcknowledge("${character.name}: $line")
    }

    override fun characterShowsEmotion(character: Character, emotion: Emotion) {
        printlnDirectTemp("${character.name} looks $emotion.")
    }

    override fun characterAnimation(character: Character, animation: Animation) {
        printlnDirectTemp("${character.name} begins $animation.")
    }

    override fun characterAsksQuestion(character: Character, question: Question) {
        var questionString = "${character.name}: ${question.line}\n"

        for (i in question.answers.indices) {
            questionString += "  ${i + 1}: ${question.answers[i].line}\n"
        }

        print(questionString)
    }

    override fun narratorAsksQuestion(narrator: Narrator, question: Question) {
        var questionString = "${question.line}\n"

        for (i in question.answers.indices) {
            questionString += "  ${i + 1}: ${question.answers[i].line}\n"
        }

        print(questionString)
    }

    override fun getAnswerQuestion(question: Question): Answer {
        var index = Int.MIN_VALUE

        while (index == Int.MIN_VALUE) {
            index = try {
                waitForInput(cancellationToken).toInt() - 1
            } catch (e: NumberFormatException) {
                Int.MIN_VALUE
            }
        }

        clear()
        return question.answers[index]
    }

    override fun playSoundEffect(soundEffect: SoundEffect) {
        when (soundEffect) {
            is ResourceSoundEffect -> {
                printlnDirectTemp("Sound effect: ${soundEffect.key}")
            }
            else -> {
                printlnDirectTemp("Sound effect: $soundEffect")
            }
        }
    }

    override fun characterMoves(character: Character, from: Position, to: Position, transition: LayoutTransition) {
        printlnDirectTemp("${character.name} moves from '$from' to '$to' with transition '$transition'.")
    }

    override fun narratorNarrates(narrator: Narrator, line: String) {
        printAndWaitForAcknowledge(line)
    }

    override fun clearScene(scene: Scene) {
        clear()
    }

    override fun enterStory(story: Story) {
        clear()
        printlnDirectTemp("Started story '${story.name}'.")
    }

    override fun exitStory(story: Story) {
        printlnDirectTemp("Ended story '${story.name}'.")
    }

    override fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        printlnDirectTemp("Started chapter '${chapter.name}' with transition $transition.")
    }

    override fun exitChapter(chapter: Chapter) {
        printlnDirectTemp("Ended chapter '${chapter.name}'.")
    }

    override fun enterScene(scene: Scene, transition: SceneTransition) {
        printlnDirectTemp("Entered scene '${scene.name}' with transition $transition.")
    }

    override fun exitScene(scene: Scene, transition: SceneTransition) {
        printlnDirectTemp("Exited scene '${scene.name}' with transition $transition.")
    }

    override fun enterStep(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken) {
        canSkipCurrentStep = canSkip
    }

    override fun exitStep(step: Step, flags: Flags) {
        // nothing
    }

    internal companion object {
        /**
         * Get the default width.
         */
        const val DEFAULT_WIDTH: Int = 50

        /**
         * Get the default lines.
         */
        const val DEFAULT_LINES: Int = 4

        /**
         * Get the ANSI color code for bright black.
         */
        const val ANSI_BRIGHT_BLACK: Int = 90

        /**
         * Get the ANSI color code for white.
         */
        const val ANSI_WHITE: Int = 90

        /**
         * The environment variable for suppressing color.
         */
        private const val NO_COLOR: String = "NO_COLOR"

        /**
         * Determine if color output should be suppressed.
         */
        private fun isColorSuppressed(): Boolean {
            // terminal color may be suppressed with the NO_COLOR environment variable
            return when (System.getenv(NO_COLOR)?.lowercase() ?: "") {
                "" -> false
                "0" -> false
                "false" -> false
                else -> true
            }
        }
    }
}
