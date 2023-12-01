package com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets

import com.github.benpollarduk.ktvn.audio.AudioManager
import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect.Companion.sfxFromResource
import com.github.benpollarduk.ktvn.audio.ResourceTrack.Companion.trackFromResource
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.audio.Track
import com.github.benpollarduk.ktvn.backgrounds.Background
import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground
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
     * Toki, a cosmonaut.
     */
    public val toki: Character = Character("Toki", configuration.gameAdapter.characterAdapter)

    /**
     * Sophie, a scholar.
     */
    public val sophie: Character = Character("Sophie", configuration.gameAdapter.characterAdapter)

    /**
     * Get the shuttle daytime background.
     */
    public val shuttleDay: Background =
        ResourceBackground.backgroundFromResource("shuttleLaunch/images/backgrounds/shuttle-day.png")

    /**
     * Get the track for the shuttle day scene.
     */
    public val shuttleDayMusic: Track = trackFromResource("shuttleLaunch/audio/shuttle-day.wav")

    /**
     * Get the 'woosh' sound effect.
     */
    public val sfxWoosh: SoundEffect = sfxFromResource("shuttleLaunch/audio/woosh.wav")
}
