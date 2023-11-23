package com.github.benpollarduk.ktvn.backgrounds

/**
 * A background from a resource, identified by a [key].
 */
public data class ResourceBackground(public var key: String) : Background {
    public companion object {
        /**
         * Create a solid background from a [key].
         */
        public infix fun backgroundFromResource(key: String): ResourceBackground {
            return ResourceBackground(key)
        }
    }
}
