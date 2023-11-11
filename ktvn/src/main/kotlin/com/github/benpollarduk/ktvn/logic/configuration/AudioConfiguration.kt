package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.audio.AudioListener

/**
 * Provides a configuration for audio.
 */
public interface AudioConfiguration {
    /**
     * Get the audio listener.
     */
    public val audioListener: AudioListener
}
