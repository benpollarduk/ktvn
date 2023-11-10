package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.audio.AudioListener

/**
 * Provides an interaction configuration.
 */
public interface InteractionConfiguration {
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
     * Get the audio listener.
     */
    public val audioListener: AudioListener
}
