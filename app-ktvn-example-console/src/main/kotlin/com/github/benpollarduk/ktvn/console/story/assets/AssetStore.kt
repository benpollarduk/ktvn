package com.github.benpollarduk.ktvn.console.story.assets

import com.github.benpollarduk.ktvn.audio.AudioManager
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.console.AnsiConsoleGameEngine
import com.github.benpollarduk.ktvn.logic.configuration.StandardGameConfiguration

/**
 * An object that acts as a store for the games assets.
 */
internal object AssetStore {
    /**
     * The engine responsible for handling all input and output.
     */
    internal val engine = AnsiConsoleGameEngine()

    /**
     *  The configuration for interacting with the game.
     */
    internal val configuration = StandardGameConfiguration(engine)

    /**
     * The narrator - responsible for narrating the story.
     */
    internal val narrator = Narrator(configuration.gameAdapter.narratorAdapter)

    /**
     * The audio manager, responsible for all audio.
     */
    internal val audio = AudioManager(configuration.gameAdapter.audioAdapter)

    /**
     * Morgana, a witch. The antagonist of the story.
     */
    internal val morgana = Character("Morgana", configuration.gameAdapter.characterAdapter)

    /**
     * Michel, a man. The protagonist of the story.
     */
    internal val michel = Character("Michel", configuration.gameAdapter.characterAdapter)
}