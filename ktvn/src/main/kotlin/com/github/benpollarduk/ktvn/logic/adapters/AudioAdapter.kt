package com.github.benpollarduk.ktvn.logic.adapters

import com.github.benpollarduk.ktvn.audio.AudioListener

/**
 * Provides an adapter for audio. The adapter allows for audio events to be passed to a receiving class.
 */
public interface AudioAdapter {
    /**
     * Get the audio listener.
     */
    public val audioListener: AudioListener
}
