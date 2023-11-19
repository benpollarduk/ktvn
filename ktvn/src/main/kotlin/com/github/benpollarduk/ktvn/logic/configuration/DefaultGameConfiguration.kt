package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTracker
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultAudioConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultCharacterConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultLayoutConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultNarratorConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultStoryConfiguration

/**
 * Provides a default configuration with a specified [gameController]. Optionally a [stepTracker] can be specified, by
 * default a [HashStepTracker] will be used.
 */
public class DefaultGameConfiguration(
    public val gameController: GameController,
    public override var stepTracker: StepTracker = HashStepTracker()
) : GameConfiguration {
    override val characterConfiguration: CharacterConfiguration = DefaultCharacterConfiguration(gameController)
    override val narratorConfiguration: NarratorConfiguration = DefaultNarratorConfiguration(gameController)
    override val layoutConfiguration: LayoutConfiguration = DefaultLayoutConfiguration(gameController)
    override val storyConfiguration: StoryConfiguration = DefaultStoryConfiguration(gameController)
    override val audioConfiguration: AudioConfiguration = DefaultAudioConfiguration(gameController)
}
