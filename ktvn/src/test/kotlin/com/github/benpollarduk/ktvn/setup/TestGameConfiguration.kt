package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTracker
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration

internal object TestGameConfiguration : GameConfiguration {
    override val stepTracker: StepTracker = StepIdentifierTracker()
    override val gameAdapter: GameAdapter = TestGameAdapter
}
