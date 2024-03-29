package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.engines.GameEngine

/**
 * Provides a configuration for a [Game].
 */
public interface GameConfiguration {
    /**
     * Get or set the game engine.
     */
    public var engine: GameEngine?

    /**
     * Get [StepTracker] used to track which [Step] have been seen.
     */
    public val stepTracker: StepTracker

    /**
     * Get the adapter for the [Game].
     */
    public val gameAdapter: GameAdapter
}
