package com.github.benpollarduk.ktvn.audio

/**
 * A track from a resource, identified by a [key]. When [loop] is set true the track should be looped on playback.
 */
public data class ResourceTrack(public var key: String, override val loop: Boolean = true) : Track {
    public companion object {
        /**
         * Create a track from a resource, identified by a [key].
         */
        public infix fun trackFromResource(key: String): ResourceTrack {
            return ResourceTrack(key)
        }
    }
}
