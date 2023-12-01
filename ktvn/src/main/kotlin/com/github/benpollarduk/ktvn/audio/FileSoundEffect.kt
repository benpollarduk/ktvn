package com.github.benpollarduk.ktvn.audio

/**
 * A sound effect from a file at a specified [path].
 */
public data class FileSoundEffect(public var path: String) : SoundEffect {
    public companion object {
        /**
         * Create a sound effect from a file at a specified [path].
         */
        public infix fun sfxFromFile(path: String): FileSoundEffect {
            return FileSoundEffect(path)
        }
    }
}
