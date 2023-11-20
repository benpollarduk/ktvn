package com.github.benpollarduk.ktvn.audio

/**
 * Provides an interface for listeners to audio events.
 */
public interface AudioListener {
    /**
     * Invoke the listener for playSoundEffect with a specified [soundEffect].
     */
    public fun sfx(soundEffect: SoundEffect)
}
