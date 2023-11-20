package com.github.benpollarduk.ktvn.logic.adapters

/**
 * Provides an adapter for a [Game]. The adapter allows for game events to be passed to a receiving class.
 */
public interface GameAdapter {
    /**
     * Get the configuration for characters.
     */
    public val characterAdapter: CharacterAdapter

    /**
     * Get the configuration for narration.
     */
    public val narratorAdapter: NarratorAdapter

    /**
     * Get the configuration for layout.
     */
    public val layoutAdapter: LayoutAdapter

    /**
     * Get the configuration for story.
     */
    public val storyAdapter: StoryAdapter

    /**
     * Get the configuration for audio.
     */
    public val audioAdapter: AudioAdapter
}
