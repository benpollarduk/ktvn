package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.logic.configuration.DynamicGameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Story

/**
 * Provides a template for producing [Story] objects.
 */
public open class StoryTemplate {
    /**.
     * Get the game configuration.
     */
    public open val configuration: DynamicGameConfiguration? = null

    /**
     * Instantiate a new instance of the templated [Story].
     */
    public open fun instantiate(): Story {
        throw NotImplementedError()
    }
}
