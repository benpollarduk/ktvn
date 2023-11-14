package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.LayoutConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration

/**
 * Provides a default configuration for an ANSI compatible Console.
 */
public object AnsiConsoleGameConfiguration : GameConfiguration {
    public val consoleController: AnsiConsoleController = AnsiConsoleController()
    override val characterConfiguration: CharacterConfiguration = AnsiConsoleCharacterConfiguration(consoleController)
    override val narratorConfiguration: NarratorConfiguration = AnsiConsoleNarratorConfiguration(consoleController)
    override val layoutConfiguration: LayoutConfiguration = AnsiConsoleLayoutConfiguration(consoleController)
    override val storyConfiguration: StoryConfiguration = AnsiConsoleStoryConfiguration(consoleController)
    override val audioConfiguration: AudioConfiguration = AnsiConsoleAudioConfiguration(consoleController)
}
