package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.audio.NoAudio.Companion.silence
import com.github.benpollarduk.ktvn.audio.ResourceTrack.Companion.trackFromResource
import com.github.benpollarduk.ktvn.audio.Track
import com.github.benpollarduk.ktvn.backgrounds.Background
import com.github.benpollarduk.ktvn.backgrounds.ColorBackground.Companion.emptyBackground
import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground.Companion.backgroundFromResource
import com.github.benpollarduk.ktvn.io.CharacterRestorePoint
import com.github.benpollarduk.ktvn.io.SceneRestorePoint
import com.github.benpollarduk.ktvn.layout.Layout
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Flags

/**
 * A scene within a [Chapter].
 */
@Suppress("TooManyFunctions")
public class Scene private constructor(setup: (Scene) -> Unit) {
    private var content: List<Step> = emptyList()
    private var transitionIn: SceneTransition = SceneTransitions.instant
    private var transitionOut: SceneTransition = SceneTransitions.instant

    /**
     * Get the music for this [Scene].
     */
    public var music: Track = silence
        private set

    /**
     * Get the background for this [Scene].
     */
    public var background: Background = emptyBackground
        private set

    /**
     * Get the layout for this [Scene].
     */
    public var layout: Layout = createLayout { }
        private set

    /**
     * Get the type of [Scene] this is.
     */
    public var type: SceneType = SceneType.Dialog
        private set

    /**
     * Get the index of the current [Step].
     */
    public var indexOfCurrentStep: Int = 0
        private set

    /**
     * Get the name of this [Scene].
     */
    public var name: String = "Scene"
        private set

    /**
     * Get the number of [Step] that make up this [Scene].
     */
    public val numberOfSteps: Int
        get() = content.size

    init {
        setup(this)
    }

    /**
     * Create a restore point for this [Scene]. The returned [SceneRestorePoint] allows this [Scene] to be restored.
     */
    internal fun createRestorePoint(): SceneRestorePoint {
        val characterRestorePoints: MutableList<CharacterRestorePoint> = mutableListOf()
        layout.toArrayOfCharacterPosition().forEach {
            characterRestorePoints.add(CharacterRestorePoint(it.character, it.character.emotion, it.position))
        }
        return SceneRestorePoint(characterRestorePoints, indexOfCurrentStep)
    }

    /**
     * Set the [music].
     */
    public infix fun music(music: Track) {
        this.music = music
    }

    /**
     * Set the music, specified by [key].
     */
    public infix fun music(key: String) {
        this.music = trackFromResource(key)
    }

    /**
     * Set the [background].
     */
    public infix fun background(background: Background) {
        this.background = background
    }

    /**
     * Set the background, specified by [key].
     */
    public infix fun background(key: String) {
        this.background = backgroundFromResource(key)
    }

    /**
     * Set the [steps] that make up this scene.
     */
    public infix fun steps(steps: List<Step>) {
        this.content = steps
    }

    /**
     * Set the [type] of scene this is.
     */
    public infix fun type(type: SceneType) {
        this.type = type
    }

    /**
     * Set the [layout] for this scene.
     */
    public infix fun layout(layout: Layout) {
        this.layout = layout
    }

    /**
     * Set the [name] for this scene.
     */
    public infix fun name(name: String) {
        this.name = name
    }

    /**
     * Set the [transition] in for this scene.
     */
    public infix fun transitionIn(transition: SceneTransition) {
        this.transitionIn = transition
    }

    /**
     * Set the [transition] out for this scene.
     */
    public infix fun transitionOut(transition: SceneTransition) {
        this.transitionOut = transition
    }

    /**
     * Begin the scene with specified [flags]. The [sceneRestorePoint] specifies where the scene restores from.
     * The [sceneListener] allows events to be invoked for this scene. A [cancellationToken] must be provided to allow
     * for the chapter to be cancelled.
     */
    internal fun begin(
        flags: Flags,
        sceneRestorePoint: SceneRestorePoint,
        sceneListener: SceneListener,
        cancellationToken: CancellationToken
    ): SceneResult {
        var indexOfCurrentStep = sceneRestorePoint.step
        var sceneResult: SceneResult? = null

        if (sceneRestorePoint != SceneRestorePoint.start) {
            layout.clear()
            sceneRestorePoint.characterRestorePoints.forEach {
                layout.add(it.character, it.position)
                it.character.looks(it.emotion)
            }
        }

        sceneListener.enter(this, transitionIn)

        while (indexOfCurrentStep < content.size) {
            when (val result = content[indexOfCurrentStep](flags, cancellationToken)) {
                StepResult.Cancelled -> {
                    sceneResult = SceneResult.Cancelled
                }
                StepResult.Continue -> {
                    indexOfCurrentStep++
                }
                is StepResult.GotoStep -> {
                    indexOfCurrentStep = content.indexOfFirst { it.name.equals(result.name, true) }
                }
                is StepResult.SelectChapter -> {
                    sceneResult = SceneResult.SelectChapter(result.name)
                }
                is StepResult.SelectScene -> {
                    sceneResult = SceneResult.SelectScene(result.name)
                }
                is StepResult.End -> {
                    sceneResult = SceneResult.End(result.ending)
                }
                is StepResult.Clear -> {
                    indexOfCurrentStep++
                    sceneListener.clear(this)
                }
            }

            if (sceneResult != null) {
                break
            }
        }

        sceneListener.exit(this, transitionOut)

        return sceneResult ?: SceneResult.Continue
    }

    public companion object {
        /**
         * Create a [Scene] with a specified [setup].
         */
        public infix fun scene(setup: (Scene) -> Unit): Scene {
            return Scene(setup)
        }
    }
}
