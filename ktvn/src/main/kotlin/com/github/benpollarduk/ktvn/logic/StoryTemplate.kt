package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Story

/**
 * Provides a template [Story].
 */
public open class StoryTemplate {
    /**.
     * Get the [GameConfiguration].
     */
    public open val configuration: GameConfiguration? = null

    /**
     * Get the [Story].
     */
    public open val story: Story? = null

    public companion object {
        /**
         * Create a new [StoryTemplate] with a specified [story] and [configuration].
         */
        public fun create(story: Story, configuration: GameConfiguration): StoryTemplate {
            return object : StoryTemplate() {
                override val configuration: GameConfiguration = configuration
                override val story: Story = story
            }
        }
    }
}
