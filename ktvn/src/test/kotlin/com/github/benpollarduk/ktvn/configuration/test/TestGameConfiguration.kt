package com.github.benpollarduk.ktvn.configuration.test

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTracker
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.LayoutConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration

internal object TestGameConfiguration : GameConfiguration {
    override val stepTracker: StepTracker = HashStepTracker()
    override val characterConfiguration: CharacterConfiguration = TestCharacterConfiguration()
    override val narratorConfiguration: NarratorConfiguration = TestNarratorConfiguration()
    override val layoutConfiguration: LayoutConfiguration = TestLayoutConfiguration()
    override val storyConfiguration: StoryConfiguration = TestStoryConfiguration()
    override val audioConfiguration: AudioConfiguration = TestAudioConfiguration()
}
