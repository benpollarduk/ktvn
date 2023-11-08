package com.github.benpollarduk.ktvn.backgrounds

/**
 * A static background, identified by a [key].
 */
public data class StaticBackground(public var key: String) : Background {
    public companion object {
        /**
         * Create a static background with a [key].
         */
        public infix fun static(key: String) : StaticBackground {
            return StaticBackground(key)
        }
    }
}
