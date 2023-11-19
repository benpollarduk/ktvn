package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.ProgressionMode

/**
 * Provides a configuration for a [Game].
 */
public interface GameConfiguration {
    /**
     * Get [StepTracker] used to track which [Step] have been seen.
     */
    public val stepTracker: StepTracker

    /**
     * Get the [ProgressionMode].
     */
    public var progressionMode: ProgressionMode

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
