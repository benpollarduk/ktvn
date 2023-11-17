package com.github.benpollarduk.ktvn.console.story.assets

import com.github.benpollarduk.ktvn.audio.AudioManager
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.console.configuration.AnsiConsoleController
import com.github.benpollarduk.ktvn.console.configuration.AnsiConsoleGameConfiguration

/**
 * An object that acts as a store for the games assets.
 */
internal object AssetStore {
    /**
     * The controller responsible for handling all input and output.
     */
    private val controller = AnsiConsoleController()

    /**
     *  The console configuration for interacting with the returnToTheMansion.
     */
    internal val configuration = AnsiConsoleGameConfiguration(controller)

    /**
     * The narrator - responsible for narrating the story.
     */
    internal val narrator = Narrator(configuration.narratorConfiguration)

    /**
     * The audio manager, responsible for all audio.
     */
    internal val audio = AudioManager(configuration.audioConfiguration)

    /**
     * Morgana, a witch. The antagonist of the story.
     */
    internal val morgana = Character("Morgana", configuration.characterConfiguration)

    /**
     * Michel, a man. The protagonist of the story.
     */
    internal val michel = Character("Michel", configuration.characterConfiguration)
}