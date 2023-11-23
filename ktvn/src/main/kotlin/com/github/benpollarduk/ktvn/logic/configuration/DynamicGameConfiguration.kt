package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTracker
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.dynamic.DynamicGameAdapter

/**
 * Provides a [GameConfiguration] with a [gameEngine] that can be specified after initialization. Optionally a
 * [stepTracker] can be specified, by default a [StepIdentifierTracker] will be used.
 */
public class DynamicGameConfiguration(
    public override var stepTracker: StepTracker = StepIdentifierTracker()
) : GameConfiguration {
    /**
     * Get or set the [GameEngine].
     */
    public override var engine: GameEngine?
        get() {
            return if (gameAdapter is DynamicGameAdapter) {
                gameAdapter.gameEngine
            } else {
                null
            }
        }
        set(value) {
            val adapter = gameAdapter as DynamicGameAdapter
            adapter.gameEngine = value
        }

    override val gameAdapter: GameAdapter = DynamicGameAdapter(engine)
}
