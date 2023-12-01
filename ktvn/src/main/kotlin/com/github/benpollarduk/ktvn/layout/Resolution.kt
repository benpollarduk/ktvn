package com.github.benpollarduk.ktvn.layout

/**
 * Provides a resolution in terms of pixels, with a specified [width] and [height].
 */
public data class Resolution(public val width: Int, public val height: Int) {
    public companion object {
        /**
         * Get a value for a not specified resolution.
         */
        public val NOT_SPECIFIED: Resolution = Resolution(0, 0)
    }
}
