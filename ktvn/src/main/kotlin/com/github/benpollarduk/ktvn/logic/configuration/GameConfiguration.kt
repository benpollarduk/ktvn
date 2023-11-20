package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter

/**
 * Provides a configuration for a [Game].
 */
public interface GameConfiguration {
    /**
     * Get [StepTracker] used to track which [Step] have been seen.
     */
    public val stepTracker: StepTracker

    /**
     * Get the adapter for the [Game].
     */
    public val gameAdapter: GameAdapter
}
