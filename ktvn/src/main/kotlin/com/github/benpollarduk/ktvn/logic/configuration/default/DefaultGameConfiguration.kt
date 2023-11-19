package com.github.benpollarduk.ktvn.logic.configuration.default

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.LayoutConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration

/**
 * Provides a default configuration with a specified [gameController] and [stepTracker].
 */
public class DefaultGameConfiguration(
    public val gameController: GameController,
    public override val stepTracker: StepTracker
) : GameConfiguration {
    override var progressionMode: ProgressionMode
        get() = gameController.progressionMode
        set(value) {
            gameController.progressionMode = value
        }

    override val characterConfiguration: CharacterConfiguration = DefaultCharacterConfiguration(gameController)
    override val narratorConfiguration: NarratorConfiguration = DefaultNarratorConfiguration(gameController)
    override val layoutConfiguration: LayoutConfiguration = DefaultLayoutConfiguration(gameController)
    override val storyConfiguration: StoryConfiguration = DefaultStoryConfiguration(gameController)
    override val audioConfiguration: AudioConfiguration = DefaultAudioConfiguration(gameController)
}
