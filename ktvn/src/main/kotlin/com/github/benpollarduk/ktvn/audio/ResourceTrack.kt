package com.github.benpollarduk.ktvn.audio

/**
 * A resource track, identified by a [key].
 */
public data class ResourceTrack(public var key: String) : Track {
    public companion object {
        /**
         * Create a resource track with a [key].
         */
        public infix fun trackFromResource(key: String): ResourceTrack {
            return ResourceTrack(key)
        }
    }
}
