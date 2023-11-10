package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.audio.AudioType

/**
 * Provides an interface for listeners to audio events.
 */
public interface AudioListener {
    /**
     * Invoke the listener for play with a specified [key].
     */
    public fun play(key: String)

    /**
     * Invoke the listener for sfx with a specified [key].
     */
    public fun sfx(key: String)

    /**
     * Invoke the listener for stop with a specified [type] of audio.
     */
    public fun stop(type: AudioType)
}
