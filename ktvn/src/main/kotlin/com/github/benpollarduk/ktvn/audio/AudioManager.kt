package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect.Companion.sfxFromResource
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration

/**
 * Provides a class for managing audio playback and control. An [audioConfiguration] must be specified.
 */
public class AudioManager(private val audioConfiguration: AudioConfiguration) {
    /**
     * Play a specified [soundEffect].
     */
    public infix fun sfx(soundEffect: SoundEffect) {
        audioConfiguration.audioListener.sfx(soundEffect)
    }

    /**
     * Play a sound effect, specified by key [key].
     */
    public infix fun sfx(key: String) {
        audioConfiguration.audioListener.sfx(sfxFromResource(key))
    }
}
