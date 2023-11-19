package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTracker
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultAudioConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultCharacterConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultLayoutConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultNarratorConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.default.DefaultStoryConfiguration

/**
 * Provides a default configuration with a specified [controller]. Optionally a [stepTracker] can be specified, by
 * default a [HashStepTracker] will be used.
 */
public class DefaultGameConfiguration(
    public val controller: GameController,
    public override var stepTracker: StepTracker = HashStepTracker()
) : GameConfiguration {
    override var progressionMode: ProgressionMode
        get() = controller.progressionMode
        set(value) {
            controller.progressionMode = value
        }

    override val characterConfiguration: CharacterConfiguration = DefaultCharacterConfiguration(controller)
    override val narratorConfiguration: NarratorConfiguration = DefaultNarratorConfiguration(controller)
    override val layoutConfiguration: LayoutConfiguration = DefaultLayoutConfiguration(controller)
    override val storyConfiguration: StoryConfiguration = DefaultStoryConfiguration(controller)
    override val audioConfiguration: AudioConfiguration = DefaultAudioConfiguration(controller)
}
