package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.scenes.Scene

/**
 * An act within a [Story], made up of [scenes].
 */
public class Act(private val scenes: List<Scene>) {
    /**
     * Begin the act.
     */
    public fun begin() {
        scenes.forEach { it.begin() }
    }
}
