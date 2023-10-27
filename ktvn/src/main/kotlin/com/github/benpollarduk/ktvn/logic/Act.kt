package com.github.benpollarduk.ktvn.logic

/**
 * An act within a [Story], made up of [scenes].
 */
public class Act(private val scenes: List<Scene>) {
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

    /**
     * Begin the act with specified [flags]. The first [scene] and [step] can be optionally specified.
     */
    public fun begin(flags: Flags, scene: Int = 0, step: Int = 0) {
        for (i in scene until scenes.size) {
            indexOfCurrentScene = i

            if (i == scene) {
                scenes[i].begin(flags, step)
            } else {
                scenes[i].begin(flags)
            }
        }
    }
}
