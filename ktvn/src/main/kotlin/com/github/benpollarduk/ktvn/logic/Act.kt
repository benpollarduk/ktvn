package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.scenes.Scene

/**
 * An act within a [Story], made up of [scenes].
 */
public class Act(private val scenes: List<Scene>) {
    /**
     * Begin the act. The first [scene] and [step] can be optionally specified.
     */
    public fun begin(scene: Int = 0, step: Int = 0) {
        for (i in scene until scenes.size) {
            if (i == scene) {
                scenes[i].begin(step)
            } else {
                scenes[i].begin()
            }
        }
    }
}
