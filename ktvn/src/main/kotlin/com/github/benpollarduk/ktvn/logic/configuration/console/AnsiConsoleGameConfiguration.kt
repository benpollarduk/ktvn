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
    /**
     * Clear the console.
     */
    public fun clearConsole() {
        print("\u001b[H\u001b[2J")
    }

    override val characterConfiguration: CharacterConfiguration = AnsiConsoleCharacterConfiguration()
    override val narratorConfiguration: NarratorConfiguration = AnsiConsoleNarratorConfiguration()
    override val layoutConfiguration: LayoutConfiguration = AnsiConsoleLayoutConfiguration()
    override val storyConfiguration: StoryConfiguration = AnsiConsoleStoryConfiguration()
    override val audioConfiguration: AudioConfiguration = AnsiConsoleAudioConfiguration()
}
