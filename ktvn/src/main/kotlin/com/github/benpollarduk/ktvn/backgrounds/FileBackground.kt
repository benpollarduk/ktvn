package com.github.benpollarduk.ktvn.backgrounds

/**
 * A background from a file at a specified [path].
 */
public data class FileBackground(public var path: String) : Background {
    public companion object {
        /**
         * Create a background from a file at a specified [path].
         */
        public infix fun backgroundFromFile(path: String): FileBackground {
            return FileBackground(path)
        }
    }
}
