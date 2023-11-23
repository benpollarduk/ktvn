package com.github.benpollarduk.ktvn.example.assets

import com.github.benpollarduk.ktvn.audio.AudioManager
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.configuration.DynamicGameConfiguration

/**
 * An object that acts as a store for the games assets.
 */
public object AssetStore {
    /**
     * Get the game configuration.
     */
    internal val configuration: DynamicGameConfiguration = DynamicGameConfiguration()

    /**
     * The narrator - responsible for narrating the story.
     */
    public val narrator: Narrator = Narrator(configuration.gameAdapter.narratorAdapter)

    /**
     * The audio manager, responsible for all audio.
     */
    public val audio: AudioManager = AudioManager(configuration.gameAdapter.audioAdapter)

    /**
     * Morgana, a witch. The antagonist of the story.
     */
    public val morgana: Character = Character("Morgana", configuration.gameAdapter.characterAdapter)

    /**
     * Michel, a man. The protagonist of the story.
     */
    public val michel: Character = Character("Michel", configuration.gameAdapter.characterAdapter)
}
