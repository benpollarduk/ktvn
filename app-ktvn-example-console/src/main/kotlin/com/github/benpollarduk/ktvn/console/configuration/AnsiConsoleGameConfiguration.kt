package com.github.benpollarduk.ktvn.console.configuration

import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTracker
import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTrackerSerializer
import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.LayoutConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration

/**
 * Provides a configuration for an ANSI compatible console that uses a [consoleController].
 */
internal class AnsiConsoleGameConfiguration(internal val consoleController: AnsiConsoleController) : GameConfiguration {
    override var progressionMode: ProgressionMode
        get() = consoleController.progressionMode
        set(value) {
            consoleController.progressionMode = value
        }

    override val characterConfiguration: CharacterConfiguration = AnsiConsoleCharacterConfiguration(consoleController)
    override val narratorConfiguration: NarratorConfiguration = AnsiConsoleNarratorConfiguration(consoleController)
    override val layoutConfiguration: LayoutConfiguration = AnsiConsoleLayoutConfiguration(consoleController)
    override val storyConfiguration: StoryConfiguration = AnsiConsoleStoryConfiguration(consoleController)
    override val audioConfiguration: AudioConfiguration = AnsiConsoleAudioConfiguration(consoleController)
    override var stepTracker = HashStepTracker()
        private set

    /**
     * Persist step data to the specified [path].
     */
    internal fun persistStepData(path: String) : SaveResult {
        return HashStepTrackerSerializer.toFile(stepTracker, path)
    }

    /**
     * Restore step data from the specified [path].
     */
    internal fun restoreStepData(path: String) : LoadResult<HashStepTracker> {
        val result = HashStepTrackerSerializer.fromFile(path)
        if (result.result) {
            stepTracker = result.loadedObject
        }
        return result
    }
}
