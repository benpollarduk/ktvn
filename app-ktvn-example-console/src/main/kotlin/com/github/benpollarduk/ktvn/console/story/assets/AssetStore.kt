package com.github.benpollarduk.ktvn.console.story.assets

import com.github.benpollarduk.ktvn.audio.AudioManager
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.console.AnsiConsoleGameController
import com.github.benpollarduk.ktvn.logic.configuration.DefaultGameConfiguration

/**
 * An object that acts as a store for the games assets.
 */
internal object AssetStore {
    /**
     * The gameController responsible for handling all input and output.
     */
    internal val controller = AnsiConsoleGameController()

    /**
     *  The configuration for interacting with the game.
     */
    internal val configuration = DefaultGameConfiguration(controller)

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