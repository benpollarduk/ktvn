package com.github.benpollarduk.ktvn.audio

/**
 * Provides a result for sound playback. Success can be indicated with [wasSuccessful] and any additional detail
 * specified with [detail].
 */
public data class SoundPlaybackResult(public val wasSuccessful: Boolean, public val detail: String) {
    public companion object {
        /**
         * Provides a default value for success.
         */
        public val SUCCESS : SoundPlaybackResult = SoundPlaybackResult(true, "")
    }
}
