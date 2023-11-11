package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration

/**
 * Provides a class for managing audio playback and control. An [audioConfiguration] must be specified.
 */
public class AudioManager(private val audioConfiguration: AudioConfiguration) {
    /**
     * Play background music, specified by [key].
     */
    public infix fun play(key: String) {
        audioConfiguration.audioListener.play(key)
    }

    /**
     * Play a sound effect, specified by [key].
     */
    public infix fun sfx(key: String) {
        audioConfiguration.audioListener.sfx(key)
    }

    /**
     * Stop all playback of a specified [type] of audio.
     */
    public infix fun stop(type: AudioType) {
        audioConfiguration.audioListener.stop(type)
    }
}
