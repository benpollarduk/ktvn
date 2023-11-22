package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTracker
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.late.LateGameAdapter

/**
 * Provides a [GameConfiguration] with a [gameEngine] that can be specified after initialization. Optionally a
 * [stepTracker] can be specified, by default a [StepIdentifierTracker] will be used.
 */
public class LateGameConfiguration(
    public var gameEngine: GameEngine? = null,
    public override var stepTracker: StepTracker = StepIdentifierTracker()
) : GameConfiguration {
    override val gameAdapter: GameAdapter = LateGameAdapter(gameEngine)
}
