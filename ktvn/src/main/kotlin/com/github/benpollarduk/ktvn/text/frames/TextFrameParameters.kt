package com.github.benpollarduk.ktvn.text.frames

import java.awt.Font

/**
 * Defines parameters for a text frames.
 */
public data class TextFrameParameters(
    public val widthConstraint: Int,
    public val availableLines: Int,
    public val font: Font = DEFAULT_FONT
) {
    public companion object {
        /**
         * Get a default value for font.
         */
        public val DEFAULT_FONT: Font = Font("Arial", Font.PLAIN, 12)
    }
}
