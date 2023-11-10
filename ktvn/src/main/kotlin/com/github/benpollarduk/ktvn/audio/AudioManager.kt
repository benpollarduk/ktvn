package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.logic.listeners.AudioListener

/**
 * Provides a class for managing audio playback and control. A listener for [audioListener] must be specified.
 */
public class AudioManager(private val audioListener: AudioListener) {
    /**
     * Play background music, specified by [key].
     */
    public infix fun bgm(key: String) {
        audioListener.play(key)
    }

    /**
     * Play a sound effect, specified by [key].
     */
    public infix fun sfx(key: String) {
        audioListener.sfx(key)
    }

    /**
     * Stop all playback of a specified [type] of audio.
     */
    public infix fun stop(type: AudioType) {
        audioListener.stop(type)
    }
}