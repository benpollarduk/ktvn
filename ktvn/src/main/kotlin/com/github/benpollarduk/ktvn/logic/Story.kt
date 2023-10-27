package com.github.benpollarduk.ktvn.logic

/**
 * A story, consisting of one or more [acts].
 */
public class Story(private val acts: List<Act>) {
    /**
     * Begin the [Story].
     */
    public fun begin() {
        acts.forEach { it.begin() }
    }
}
