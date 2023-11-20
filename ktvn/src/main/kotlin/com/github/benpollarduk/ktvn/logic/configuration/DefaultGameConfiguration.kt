package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTracker
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.default.DefaultGameAdapter

/**
 * Provides a default adapter with a specified [gameEngine]. Optionally a [stepTracker] can be specified, by
 * default a [StepIdentifierTracker] will be used.
 */
public class DefaultGameConfiguration(
    public val gameEngine: GameEngine,
    public override var stepTracker: StepTracker = StepIdentifierTracker()
) : GameConfiguration {
    override val gameAdapter: GameAdapter = DefaultGameAdapter(gameEngine)
}
