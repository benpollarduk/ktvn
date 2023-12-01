package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.audio.NoAudio.Companion.silence
import com.github.benpollarduk.ktvn.audio.ResourceTrack.Companion.trackFromResource
import com.github.benpollarduk.ktvn.audio.Track
import com.github.benpollarduk.ktvn.backgrounds.Background
import com.github.benpollarduk.ktvn.backgrounds.ColorBackground.Companion.empty
import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground.Companion.backgroundFromResource
import com.github.benpollarduk.ktvn.io.restore.CharacterRestorePoint
import com.github.benpollarduk.ktvn.io.restore.SceneRestorePoint
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.layout.Layout
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.structure.SceneTypes.dialog
import com.github.benpollarduk.ktvn.logic.structure.steps.Pause
import com.github.benpollarduk.ktvn.logic.structure.steps.Then

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
    public var background: Background = empty
        private set

    /**
     * Get the layout for this [Scene].
     */
    public var layout: Layout = createLayout { }
        private set

    /**
     * Get the type of [Scene] this is.
     */
    public var type: SceneType = dialog
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
     * Get all steps in this scene.
     */
    internal fun getAllSteps(): List<Step> {
        return content.toList()
    }

    /**
     * Determine if a [step] Can be skipped. Skipping depends on a combination of the type of step and if it has
     * already been seen.
     */
    internal fun canSkipStep(step: Step, stepTracker: StepTracker): Boolean {
        return when (step) {
            is Then -> stepTracker.hasBeenSeen(step)
            is Pause -> stepTracker.hasBeenSeen(step)
            else -> false
        }
    }

    /**
     * Restore this [Scene] from a [sceneRestorePoint].
     */
    private fun restore(sceneRestorePoint: SceneRestorePoint) {
        if (sceneRestorePoint != SceneRestorePoint.START) {
            layout.clear()
            sceneRestorePoint.characterRestorePoints.forEach {
                layout.add(it.character, it.position)
                it.character.looks(it.emotion)
            }
        }
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
     * Begin the scene with specified [parameters]. The [sceneListener] allows events to be invoked for this
     * scene. The [stepListener] allows events to be invoked for steps within the scene. Returns a [SceneResult].
     */
    internal fun begin(
        parameters: SceneBeginParameters,
        sceneListener: SceneListener,
        stepListener: StepListener
    ): SceneResult {
        indexOfCurrentStep = maxOf(0, minOf(content.size - 1, parameters.sceneRestorePoint.step - 1))
        var sceneResult: SceneResult? = null
        restore(parameters.sceneRestorePoint)
        sceneListener.enter(this, transitionIn)

        while (indexOfCurrentStep < content.size) {
            val step = content[indexOfCurrentStep]
            stepListener.enter(
                step,
                parameters.flags,
                canSkipStep(step, parameters.stepTracker),
                parameters.cancellationToken
            )

            val result = step(parameters.flags, parameters.cancellationToken)
            parameters.stepTracker.registerStepSeen(step)

            when (result) {
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

            stepListener.exit(step, parameters.flags)

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
