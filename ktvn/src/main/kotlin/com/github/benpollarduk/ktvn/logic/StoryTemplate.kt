package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.logic.structure.Story

/**
 * Provides a template for producing [Story] objects.
 */
public open class StoryTemplate {
    /**
     * Instantiate a new instance of the templated [Story].
     */
    public open fun instantiate(): Story {
        throw NotImplementedError()
    }
}