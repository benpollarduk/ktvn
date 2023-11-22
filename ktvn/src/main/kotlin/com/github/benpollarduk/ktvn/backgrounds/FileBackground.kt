package com.github.benpollarduk.ktvn.backgrounds

/**
 * A background from a file, identified by a [path].
 */
public data class FileBackground(public var path: String) : Background {
    public companion object {
        /**
         * Create a solid background from a [path].
         */
        public infix fun backgroundFromFile(path: String): FileBackground {
            return FileBackground(path)
        }
    }
}
