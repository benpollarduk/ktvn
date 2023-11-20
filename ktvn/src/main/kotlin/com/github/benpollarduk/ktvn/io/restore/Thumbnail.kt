package com.github.benpollarduk.ktvn.io.restore

/**
 * Provides a thumbnail in 24-bit RGB format with a specified [width], [height] and [rgbBytes].
 */
public data class Thumbnail(
    public val width: Int,
    public val height: Int,
    public val rgbBytes: List<Byte>
) {
    public companion object {
        /**
         * Get a default value for no thumbnail.
         */
        public val none: Thumbnail = Thumbnail(0, 0, emptyList())
    }
}
