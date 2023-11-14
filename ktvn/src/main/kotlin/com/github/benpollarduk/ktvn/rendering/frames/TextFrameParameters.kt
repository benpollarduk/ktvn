package com.github.benpollarduk.ktvn.rendering.frames

import java.awt.Font

/**
 * Defines parameters for a text frames.
 */
public data class TextFrameParameters(
    public val widthConstraint: Int,
    public val availableLines: Int,
    public val font: Font = defaultFont
) {
    public companion object {
        /**
         * Get a default value for font.
         */
        public val defaultFont: Font = Font("Arial", Font.PLAIN, 12)
    }
}
