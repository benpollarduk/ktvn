package com.github.benpollarduk.ktvn.console.configuration

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
    override val characterConfiguration: CharacterConfiguration = AnsiConsoleCharacterConfiguration(consoleController)
    override val narratorConfiguration: NarratorConfiguration = AnsiConsoleNarratorConfiguration(consoleController)
    override val layoutConfiguration: LayoutConfiguration = AnsiConsoleLayoutConfiguration(consoleController)
    override val storyConfiguration: StoryConfiguration = AnsiConsoleStoryConfiguration(consoleController)
    override val audioConfiguration: AudioConfiguration = AnsiConsoleAudioConfiguration(consoleController)
}
