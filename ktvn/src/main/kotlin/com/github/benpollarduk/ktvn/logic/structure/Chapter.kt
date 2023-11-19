package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.io.restore.ChapterRestorePoint

/**
 * A chapter within a [Story].
 */
public class Chapter private constructor(setup: (Chapter) -> Unit) {
    private val scenes: MutableList<Scene> = mutableListOf()
    private var transition: ChapterTransition = ChapterTransitions.instant

    /**
     * Get the name of this [Chapter].
     */
    public var name: String = "Chapter"
        private set

    /**
     * Get the number of the [Scene] in this [Chapter].
     */
    public val numberOfScenes: Int
        get() = scenes.size

    /**
     * Get the index of the current [Scene].
     */
    internal var indexOfCurrentScene: Int = 0
        private set

    /**
     * Get the current [Scene].
     */
    internal val currentScene: Scene
        get() = scenes[indexOfCurrentScene]

    init {
        setup(this)
    }

    /**
     * Create a restore point for this [Chapter]. The returned [ChapterRestorePoint] allows this [Chapter] to be
     * restored.
     */
    internal fun createRestorePoint(): ChapterRestorePoint {
        return ChapterRestorePoint(currentScene.createRestorePoint(), indexOfCurrentScene)
    }

    /**
     * Begin the chapter with specified [parameters]. The [sceneListener] allows events to be invoked for
     * scenes within this chapter. The [chapterListener] allows events to be invoked for this chapter.  Returns a
     * [ChapterResult].
     */
    internal fun begin(
        parameters: ChapterBeginParameters,
        sceneListener: SceneListener,
        chapterListener: ChapterListener
    ): ChapterResult {
        indexOfCurrentScene = parameters.chapterRestorePoint.scene
        var chapterResult: ChapterResult? = null

        chapterListener.enter(this, transition)

        while (indexOfCurrentScene < scenes.size) {
            val currentScene = scenes[indexOfCurrentScene]

            val result = if (indexOfCurrentScene == parameters.chapterRestorePoint.scene) {
                currentScene.begin(
                    SceneBeginParameters(
                        parameters.flags,
                        parameters.chapterRestorePoint.sceneRestorePoint,
                        parameters.stepTracker,
                        parameters.progressionMode,
                        parameters.cancellationToken
                    ),
                    sceneListener
                )
            } else {
                currentScene.begin(
                    SceneBeginParameters(
                        parameters.flags,
                        ChapterRestorePoint.start.sceneRestorePoint,
                        parameters.stepTracker,
                        parameters.progressionMode,
                        parameters.cancellationToken
                    ),
                    sceneListener
                )
            }

            when (result) {
                SceneResult.Cancelled -> {
                    chapterResult = ChapterResult.Cancelled
                }
                SceneResult.Continue -> {
                    indexOfCurrentScene++
                }
                is SceneResult.SelectChapter -> {
                    chapterResult = ChapterResult.SelectChapter(result.name)
                }
                is SceneResult.SelectScene -> {
                    indexOfCurrentScene = scenes.indexOfFirst { it.name.equals(result.name, true) }
                }
                is SceneResult.End -> {
                    chapterResult = ChapterResult.End(result.ending)
                }
            }

            if (chapterResult != null) {
                break
            }
        }

        chapterListener.exit(this)

        return chapterResult ?: ChapterResult.Continue
    }

    /**
     * Set the [name] of this [Chapter].
     */
    public infix fun name(name: String) {
        this.name = name
    }

    /**
     * Add a [scene] to this [Chapter].
     */
    public infix fun add(scene: Scene) {
        scenes.add(scene)
    }

    /**
     * Set the [transition] for this chapter.
     */
    public infix fun transition(transition: ChapterTransition) {
        this.transition = transition
    }

    public companion object {
        /**
         * Create a new [Chapter] with a specified [setup].
         */
        public infix fun chapter(setup: (Chapter) -> Unit): Chapter {
            return Chapter(setup)
        }
    }
}
