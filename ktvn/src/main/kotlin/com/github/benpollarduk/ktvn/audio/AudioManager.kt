package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect.Companion.sfxFromResource
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter

/**
 * Provides a class for managing audio playback and control. An [audioAdapter] must be specified.
 */
public class AudioManager(private val audioAdapter: AudioAdapter) {
    /**
     * Play a specified [soundEffect].
     */
    public infix fun sfx(soundEffect: SoundEffect) {
        audioAdapter.audioListener.sfx(soundEffect)
    }

    /**
     * Play a sound effect, specified by key [key].
     */
    public infix fun sfx(key: String) {
        audioAdapter.audioListener.sfx(sfxFromResource(key))
    }
}
