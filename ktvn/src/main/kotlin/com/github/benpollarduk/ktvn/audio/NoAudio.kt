package com.github.benpollarduk.ktvn.audio

/**
 * No audio.
 */
public class NoAudio private constructor() : Track {
    override val loop: Boolean = false

    public companion object {
        /**
         * Default value for silence.
         */
        public val silence: NoAudio = NoAudio()
    }
}
