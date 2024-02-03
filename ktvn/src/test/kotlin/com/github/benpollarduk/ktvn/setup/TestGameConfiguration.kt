package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTracker
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.engines.GameEngine

internal object TestGameConfiguration : GameConfiguration {
    override var engine: GameEngine? = null
    override val stepTracker: StepTracker = StepIdentifierTracker()
    override val gameAdapter: GameAdapter = TestGameAdapter
}
