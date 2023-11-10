package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Flags

/**
 * A chapter within a [Story].
 */
public class Chapter private constructor(setup: (Chapter) -> Unit) {
    private val scenes: MutableList<Scene> = mutableListOf()

    /**
     * Get the name of this [Chapter].
     */
    public var name: String = "Chapter"
        private set

    /**
     * Get the index of the current [Scene].
     */
    public var indexOfCurrentScene: Int = 0
        private set

    /**
     * Get the number of the [Scene] in this [Chapter].
     */
    public val numberOfScenes: Int
        get() = scenes.size

    /**
     * Get the current [Scene].
     */
    public val currentScene: Scene
        get() = scenes[indexOfCurrentScene]

    init {
        setup(this)
    }

    /**
     * Begin the chapter with specified [flags]. The first [scene] and [step] can be optionally specified.
     * The [chapterListener] allows events to be invoked for this chapter. A [cancellationToken] must be provided to
     * allow for the chapter to be cancelled.
     */
    @Suppress("LongParameterList")
    internal fun begin(
        flags: Flags,
        scene: Int = 0,
        step: Int = 0,
        sceneListener: SceneListener,
        chapterListener: ChapterListener,
        cancellationToken: CancellationToken
    ): ChapterResult {
        chapterListener.enter(this)

        indexOfCurrentScene = scene
        var chapterResult: ChapterResult? = null

        while (indexOfCurrentScene < scenes.size) {
            val currentScene = scenes[indexOfCurrentScene]

            val result = if (indexOfCurrentScene == scene) {
                currentScene.begin(flags, step, sceneListener, cancellationToken)
            } else {
                currentScene.begin(flags, sceneListener = sceneListener, cancellationToken = cancellationToken)
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

    public companion object {
        /**
         * Create a new [Chapter] with a specified [setup].
         */
        public infix fun chapter(setup: (Chapter) -> Unit): Chapter {
            return Chapter(setup)
        }
    }
}
