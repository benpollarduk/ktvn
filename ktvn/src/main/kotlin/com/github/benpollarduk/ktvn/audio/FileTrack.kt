package com.github.benpollarduk.ktvn.audio

/**
 * A track from a file at a specified [path]. When [loop] is set true the track should be looped on playback.
 */
public data class FileTrack(public var path: String, override val loop: Boolean = true) : Track {
    public companion object {
        /**
         * Create a track from a file at a specified [path].
         */
        public infix fun trackFromFile(path: String): FileTrack {
            return FileTrack(path)
        }
    }
}
