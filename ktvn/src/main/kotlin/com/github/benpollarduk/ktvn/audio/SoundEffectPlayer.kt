package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect.Companion.soundEffectFromResource
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter

/**
 * Provides a class for playing sound effects. An [adapter] must be specified.
 */
public class SoundEffectPlayer(private val adapter: AudioAdapter) {
    /**
     * Play a specified [soundEffect].
     */
    public infix fun sfx(soundEffect: SoundEffect) {
        adapter.audioListener.sfx(soundEffect)
    }

    /**
     * Play a sound effect, specified by key [key].
     */
    public infix fun sfx(key: String) {
        adapter.audioListener.sfx(soundEffectFromResource(key))
    }
}
