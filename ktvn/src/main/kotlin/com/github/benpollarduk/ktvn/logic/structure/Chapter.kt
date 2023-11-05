package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Flags

/**
 * A chapter within a [Story]. A [setup] must be provided.
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
     * Get the current [Scene].
     */
    public val currentScene: Scene
        get() = scenes[indexOfCurrentScene]

    init {
        setup(this)
    }

    /**
     * Begin the chapter with specified [flags]. The first [scene] and [step] can be optionally specified.
     */
    @Suppress("ReturnCount")
    internal fun begin(
        flags: Flags,
        scene: Int = 0,
        step: Int = 0,
        sceneListener: SceneListener
    ): ChapterResult {
        indexOfCurrentScene = scene

        while (indexOfCurrentScene < scenes.size) {
            val currentScene = scenes[indexOfCurrentScene]
            sceneListener.enter(currentScene)

            val result = if (indexOfCurrentScene == scene) {
                currentScene.begin(flags, step)
            } else {
                currentScene.begin(flags)
            }

            sceneListener.exit(currentScene)

            when (result) {
                is SceneResult.Continue -> { indexOfCurrentScene++ }
                is SceneResult.SelectChapter -> {
                    return ChapterResult.SelectChapter(result.name)
                }
                is SceneResult.SelectScene -> {
                    indexOfCurrentScene = scenes.indexOfFirst { it.name.equals(result.name, true) }
                }
                is SceneResult.End -> {
                    return ChapterResult.End(result.ending)
                }
            }
        }

        return ChapterResult.Continue
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
