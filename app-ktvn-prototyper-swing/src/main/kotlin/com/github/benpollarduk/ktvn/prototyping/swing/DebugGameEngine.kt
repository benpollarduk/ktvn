package com.github.benpollarduk.ktvn.prototyping.swing

import com.github.benpollarduk.ktvn.audio.FileSoundEffect
import com.github.benpollarduk.ktvn.audio.FileTrack
import com.github.benpollarduk.ktvn.audio.NoAudio
import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect
import com.github.benpollarduk.ktvn.audio.ResourceTrack
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.audio.Track
import com.github.benpollarduk.ktvn.backgrounds.ColorBackground
import com.github.benpollarduk.ktvn.backgrounds.FileBackground
import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.io.CharacterResourceLookup
import com.github.benpollarduk.ktvn.io.LazyCharacterResourceLookup
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.ProgressionController
import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.VisualNovel
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepIdentifier
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.prototyping.swing.components.AnswerPicker
import com.github.benpollarduk.ktvn.prototyping.swing.components.EventTerminal
import com.github.benpollarduk.ktvn.prototyping.swing.components.FlagViewer
import com.github.benpollarduk.ktvn.prototyping.swing.components.ResourceTracker
import com.github.benpollarduk.ktvn.prototyping.swing.components.SequencedTextArea
import com.github.benpollarduk.ktvn.prototyping.swing.components.Status
import com.github.benpollarduk.ktvn.prototyping.swing.components.VisualScene
import com.github.benpollarduk.ktvn.text.frames.SizeConstrainedTextFrame
import com.github.benpollarduk.ktvn.text.frames.TextFrame
import com.github.benpollarduk.ktvn.text.frames.TextFrameParameters
import com.github.benpollarduk.ktvn.text.log.Log
import com.github.benpollarduk.ktvn.text.sequencing.AppenderTextSequencer
import com.github.benpollarduk.ktvn.text.sequencing.SequencedTextController
import com.github.benpollarduk.ktvn.text.sequencing.SequencedTextControllerListener
import java.awt.Color

/**
 * A class that functions as an engine for the debugger.
 */
@Suppress("TooManyFunctions", "MagicNumber", "LongParameterList")
class DebugGameEngine(
    private val eventTerminal: EventTerminal,
    private val visualScene: VisualScene,
    private val sequencedTextArea: SequencedTextArea,
    private val answerPicker: AnswerPicker,
    private val status: Status,
    private val resourceTracker: ResourceTracker,
    private val flagViewer: FlagViewer,
    private val allowAcknowledge: (Boolean) -> Unit
) : GameEngine {
    private var characterResourceLookup: CharacterResourceLookup? = null
    private var canSkipCurrentStep = false
    private var cancellationToken: CancellationToken = CancellationToken()
    private var location = ""
    private val sfxSoundPlayer = SoundPlayer()
    private val musicSoundPlayer = SoundPlayer()
    private var imageResolver: ImageResolver? = null
    private var structure: Array<StepIdentifier> = emptyArray()

    /**
     * Get the text controller. This is responsible for sequencing text output.
     */
    val textController = SequencedTextController(
        AppenderTextSequencer(SequencedTextController.DEFAULT_MS_BETWEEN_CHARACTERS) {
            for (c in it) {
                sequencedTextArea.print(c)
            }
        }
    )

    override val log: Log = Log()
    override val progressionController: ProgressionController = ProgressionController()

    init {
        textController.addListener(object : SequencedTextControllerListener {
            override fun startedFrame(frame: TextFrame) {
                val mode = progressionController.progressionMode
                if (mode is ProgressionMode.Skip && (canSkipCurrentStep || mode.skipUnseen)) {
                    allowAcknowledge(false)
                    textController.skip()
                } else {
                    allowAcknowledge(true)
                }
            }

            override fun finishedFrame(frame: TextFrame) {
                // no handling
            }

            override fun waitFor() {
                allowAcknowledge(true)
                // wait for enter to be pressed before continuing
                waitForAcknowledge(cancellationToken)
                allowAcknowledge(false)
            }
        })
    }

    /**
     * Get the structure of the [VisualNovel] this engine is currently set up for as an array of [StepIdentifier].
     */
    fun getVisualNovelStructure(): Array<StepIdentifier> {
        return structure
    }

    /**
     * Set up for a [visualNovel].
     */
    fun setupForVisualNovel(visualNovel: VisualNovel) {
        log.clear()
        characterResourceLookup = visualNovel.characterResourceLookup
        structure = visualNovel.structure
        imageResolver = ImageResolver(
            characterResourceLookup ?: LazyCharacterResourceLookup(),
            eventTerminal,
            resourceTracker,
            visualScene
        )
        musicSoundPlayer.stop()
        sfxSoundPlayer.stop()
    }

    /**
     * Set up for no visual novel
     */
    fun setupForNoVisualNovel() {
        log.clear()
        characterResourceLookup = null
        structure = emptyArray()
        imageResolver = null
        musicSoundPlayer.stop()
        sfxSoundPlayer.stop()
    }

    private fun setSceneBackground(sceneBackground: com.github.benpollarduk.ktvn.backgrounds.Background) {
        val backgroundImage = when (sceneBackground) {
            is ResourceBackground -> imageResolver?.getBackgroundFromResource(sceneBackground.key, location)
            is ColorBackground -> imageResolver?.getBackgroundFromColor(sceneBackground.color)
            is FileBackground -> imageResolver?.getBackgroundFromFile(sceneBackground.path, location)
            else -> null
        } ?: ImageResolver.getImageFromColor(
            Color.GRAY,
            visualScene.desiredResolution.width,
            visualScene.desiredResolution.height
        )

        visualScene.setBackgroundImage(backgroundImage)
    }

    private fun playSceneMusic(track: Track) {
        when (track) {
            is ResourceTrack -> {
                if (musicSoundPlayer.playFromResource(track.key, track.loop)) {
                    resourceTracker.registerResourceLocated(track.key, location, ResourceType.MUSIC)
                    eventTerminal.println(Severity.INFO, "Playing ${track.key}.")
                } else {
                    resourceTracker.registerResourceMissing(track.key, location, ResourceType.MUSIC)
                    eventTerminal.println(Severity.ERROR, "Resource not found: ${track.key}.")
                }
            }
            is FileTrack -> {
                if (musicSoundPlayer.playFromFile(track.path, track.loop)) {
                    resourceTracker.registerResourceLocated(track.path, location, ResourceType.MUSIC)
                    eventTerminal.println(Severity.INFO, "Playing ${track.path}.")
                } else {
                    resourceTracker.registerResourceMissing(track.path, location, ResourceType.MUSIC)
                    eventTerminal.println(Severity.ERROR, "File not found: ${track.path}.")
                }
            }
            is NoAudio -> {
                musicSoundPlayer.stop()
            }
        }
    }

    private fun print(string: String) {
        sequencedTextArea.clear()
        val frames = SizeConstrainedTextFrame.create(
            string,
            TextFrameParameters(sequencedTextArea.areaWidth, sequencedTextArea.areaHeight, sequencedTextArea.areaFont)
        )

        textController.render(frames)
    }

    fun generalAcknowledge() {
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
        when (soundEffect) {
            is ResourceSoundEffect -> {
                if (sfxSoundPlayer.playFromResource(soundEffect.key)) {
                    resourceTracker.registerResourceLocated(soundEffect.key, location, ResourceType.SOUND_EFFECT)
                    eventTerminal.println(Severity.INFO, "Played ${soundEffect.key}.")
                } else {
                    resourceTracker.registerResourceMissing(soundEffect.key, location, ResourceType.SOUND_EFFECT)
                    eventTerminal.println(Severity.ERROR, "Resource not found: ${soundEffect.key}.")
                }
            }
            is FileSoundEffect -> {
                if (sfxSoundPlayer.playFromFile(soundEffect.path)) {
                    resourceTracker.registerResourceLocated(soundEffect.path, location, ResourceType.SOUND_EFFECT)
                    eventTerminal.println(Severity.INFO, "Played ${soundEffect.path}.")
                } else {
                    resourceTracker.registerResourceMissing(soundEffect.path, location, ResourceType.SOUND_EFFECT)
                    eventTerminal.println(Severity.ERROR, "File not found: ${soundEffect.path}.")
                }
            }
        }
    }

    override fun characterAsksQuestion(character: Character, question: Question) {
        sequencedTextArea.styleFor(character)
        print(question.line)
    }

    override fun narratorAsksQuestion(narrator: Narrator, question: Question) {
        sequencedTextArea.styleFor(narrator)
        print(question.line)
    }

    override fun getAnswerQuestion(question: Question): Answer {
        return answerPicker.getAnswer(question)
    }

    override fun characterSpeaks(character: Character, line: String) {
        sequencedTextArea.styleFor(character)
        print(line)
        waitForAcknowledge(cancellationToken)
    }

    override fun characterShowsEmotion(character: Character, emotion: Emotion) {
        eventTerminal.println(Severity.INFO, "${character.name} looks $emotion.")
        val image = imageResolver?.getCharacterImage(character, emotion, location)
            ?: ImageResolver.getMissingCharacterResourceImage(
                Color.BLUE,
                visualScene.desiredResolution.width / 4,
                (visualScene.desiredResolution.height * 0.75).toInt()
            )
        visualScene.addOrUpdate(character, image)
    }

    override fun characterAnimation(character: Character, animation: Animation) {
        eventTerminal.println(Severity.INFO, "${character.name} animated with $animation.")
        visualScene.animateCharacter(character, animation)
    }

    override fun characterMoves(character: Character, from: Position, to: Position) {
        eventTerminal.println(Severity.INFO, "${character.name} moves from '$from' to '$to'.")
        val image = imageResolver?.getCharacterImage(character, character.emotion, location)
            ?: ImageResolver.getMissingCharacterResourceImage(
                Color.BLUE,
                visualScene.desiredResolution.width / 4,
                (visualScene.desiredResolution.height * 0.75).toInt()
            )
        visualScene.addOrUpdate(character, image)
        visualScene.moveCharacter(character, from, to)
    }

    override fun narratorNarrates(narrator: Narrator, line: String) {
        sequencedTextArea.styleFor(narrator)
        print(line)
        waitForAcknowledge(cancellationToken)
    }

    override fun enterStory(story: Story) {
        location = "Story: ${story.name}"
        eventTerminal.println(Severity.INFO, "Started story '${story.name}'.")
    }

    override fun exitStory(story: Story) {
        eventTerminal.println(Severity.INFO, "Ended story '${story.name}'.")
    }

    override fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        location = "Chapter: ${chapter.name}"
        eventTerminal.println(Severity.INFO, "Started chapter '${chapter.name}' with transition $transition.")
    }

    override fun exitChapter(chapter: Chapter) {
        eventTerminal.println(Severity.INFO, "Finished chapter '${chapter.name}'.")
    }

    override fun enterScene(scene: Scene, transition: SceneTransition) {
        location = "Scene: ${scene.name}"

        setSceneBackground(scene.background)
        if (scene.music is NoAudio) {
            musicSoundPlayer.stop()
        } else {
            playSceneMusic(scene.music)
        }

        eventTerminal.println(Severity.INFO, "Started scene '${scene.name}' with transition $transition.")
    }

    override fun exitScene(scene: Scene, transition: SceneTransition) {
        eventTerminal.println(Severity.INFO, "Finished scene '${scene.name}' with transition $transition.")
    }

    override fun clearScene(scene: Scene) {
        eventTerminal.println(Severity.INFO, "Clearing scene '${scene.name}'.")
        visualScene.reset()
    }

    override fun enterStep(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken) {
        location = "Step: ${step.identifier}"
        eventTerminal.println(Severity.INFO, "Started step '${step.name}' (${step.identifier}).")
        canSkipCurrentStep = canSkip
        this.cancellationToken = cancellationToken
        status.updatePosition(step.identifier)
        flagViewer.updateFlags(flags)
    }

    override fun exitStep(step: Step, flags: Flags) {
        eventTerminal.println(Severity.INFO, "Finished step '${step.name}' (${step.identifier}).")
        flagViewer.updateFlags(flags)
    }
}
