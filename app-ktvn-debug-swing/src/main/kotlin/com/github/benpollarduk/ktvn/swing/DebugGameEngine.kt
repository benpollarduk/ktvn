package com.github.benpollarduk.ktvn.swing

import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.backgrounds.ColorBackground
import com.github.benpollarduk.ktvn.backgrounds.FileBackground
import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.ProgressionController
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.swing.components.Background
import com.github.benpollarduk.ktvn.swing.components.EventTerminal
import com.github.benpollarduk.ktvn.swing.components.ProgressionControl
import com.github.benpollarduk.ktvn.swing.components.SequencedTextArea
import com.github.benpollarduk.ktvn.text.frames.SizeConstrainedTextFrame
import com.github.benpollarduk.ktvn.text.frames.TextFrameParameters
import com.github.benpollarduk.ktvn.text.log.Log
import com.github.benpollarduk.ktvn.text.sequencing.SequencedTextController
import com.github.benpollarduk.ktvn.text.sequencing.TimeBasedTextSequencer
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * A class that functions as an engine for the debugger.
 */
@Suppress("TooManyFunctions", "MagicNumber")
public class DebugGameEngine(
    private val eventTerminal: EventTerminal,
    private val background: Background,
    private val sequencedTextArea: SequencedTextArea
) : GameEngine {
    private val textController = SequencedTextController(
        TimeBasedTextSequencer {
            for (position in it) {
                sequencedTextArea.print(position.character, position.column, position.row)
            }
        }
    )
    private var canSkipCurrentStep = false
    private var cancellationToken: CancellationToken = CancellationToken()

    override val log: Log = Log()
    override val progressionController: ProgressionController = ProgressionController()

    private fun print(string: String) {
        sequencedTextArea.clear()
        val frames = SizeConstrainedTextFrame.create(
            string,
            TextFrameParameters(sequencedTextArea.areaWidth, sequencedTextArea.areaHeight, sequencedTextArea.areaFont)
        )

        textController.render(frames)
    }

    private fun getBackgroundFromFile(path: String): BufferedImage? {
        val imageFile = File(path)

        return if (imageFile.exists()) {
            ImageIO.read(imageFile)
        } else {
            eventTerminal.println(Severity.ERROR, "Image file not found: $path")
            null
        }
    }

    private fun getBackgroundFromResource(key: String): BufferedImage? {
        val inputStream = javaClass.getResourceAsStream(key)
        return if (inputStream != null) {
            ImageIO.read(inputStream)
        } else {
            eventTerminal.println(Severity.ERROR, "Image resource not found: $key")
            null
        }
    }

    private fun getBackgroundFromColor(color: Color): BufferedImage {
        val image = BufferedImage(background.resolutionWidth, background.resolutionHeight, BufferedImage.TYPE_INT_RGB)
        val g = image.createGraphics()
        g.color = color
        g.fillRect(0, 0, background.resolutionWidth, background.resolutionHeight)
        g.dispose()
        return image
    }

    public fun generalAcknowledge() {
        if (textController.sequencing) {
            textController.skip()
        } else {
            progressionController.triggerAcknowledgementLatch()
        }
    }

    private fun waitForAcknowledge(cancellationToken: CancellationToken) {
        progressionController.awaitAcknowledgement(canSkipCurrentStep, cancellationToken)
    }

    override fun playSoundEffect(soundEffect: SoundEffect) {
        // nothing
    }

    override fun waitForCharacterEmotionAcknowledgement() {
        // pass through
    }

    override fun waitForCharacterAnimationAcknowledgement() {
        // pass through
    }

    override fun waitForCharacterSpeakAcknowledgement() {
        waitForAcknowledge(cancellationToken)
    }

    override fun waitForNarratorNarrateAcknowledgement() {
        waitForAcknowledge(cancellationToken)
    }

    override fun waitForLayoutMovementAcknowledgement() {
        // pass through
    }

    override fun characterAsksQuestion(character: Character, question: Question) {
        // nothing
    }

    override fun narratorAsksQuestion(narrator: Narrator, question: Question) {
        // nothing
    }

    override fun answerQuestion(question: Question): Answer {
        return question.answers.first()
    }

    override fun characterSpeaks(character: Character, line: String) {
        print("${character.name}: $line")
    }

    override fun characterShowsEmotion(character: Character, emotion: Emotion) {
        // nothing
    }

    override fun characterAnimation(character: Character, animation: Animation) {
        // nothing
    }

    override fun characterMoves(character: Character, from: Position, to: Position) {
        // nothing
    }

    override fun narratorNarrates(narrator: Narrator, line: String) {
        print(line)
    }

    override fun enterStory(story: Story) {
        eventTerminal.println(Severity.INFO, "Started story '${story.name}'.")
    }

    override fun exitStory(story: Story) {
        eventTerminal.println(Severity.INFO, "Ended story '${story.name}'.")
    }

    override fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        eventTerminal.println(Severity.INFO, "Started chapter '${chapter.name}' with transition $transition.")
    }

    override fun exitChapter(chapter: Chapter) {
        eventTerminal.println(Severity.INFO, "Finished chapter '${chapter.name}'.")
    }

    override fun enterScene(scene: Scene, transition: SceneTransition) {
        val backgroundImage = when (val bk = scene.background) {
            is ResourceBackground -> getBackgroundFromResource(bk.key)
            is ColorBackground -> getBackgroundFromColor(bk.color)
            is FileBackground -> getBackgroundFromFile(bk.path)
            else -> null
        } ?: getBackgroundFromColor(Color.GRAY)

        background.setImage(backgroundImage)
        eventTerminal.println(Severity.INFO, "Started scene '${scene.name}' with transition $transition.")
    }

    override fun exitScene(scene: Scene, transition: SceneTransition) {
        eventTerminal.println(Severity.INFO, "Finished scene '${scene.name}' with transition $transition.")
    }

    override fun clearScene(scene: Scene) {
        // nothing
    }

    override fun enterStep(step: Step, canSkip: Boolean, cancellationToken: CancellationToken) {
        eventTerminal.println(Severity.INFO, "Started step '${step.name}' (${step.identifier}).")
        canSkipCurrentStep = canSkip
        this.cancellationToken = cancellationToken
    }

    override fun exitStep(step: Step) {
        eventTerminal.println(Severity.INFO, "Finished step '${step.name}' (${step.identifier}).")
    }
}
