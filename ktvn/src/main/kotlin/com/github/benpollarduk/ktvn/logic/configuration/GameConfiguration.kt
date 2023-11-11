package com.github.benpollarduk.ktvn.logic.configuration

/**
 * Provides a configuration for a [Game].
 */
public interface GameConfiguration {
    /**
     * Get the configuration for characters.
     */
    public val characterConfiguration: CharacterConfiguration

    /**
     * Get the configuration for narration.
     */
    public val narratorConfiguration: NarratorConfiguration

    /**
     * Get the configuration for layout.
     */
    public val layoutConfiguration: LayoutConfiguration

    /**
     * Get the configuration for story.
     */
    public val storyConfiguration: StoryConfiguration

    /**
     * Get the configuration for audio.
     */
    public val audioConfiguration: AudioConfiguration
}
