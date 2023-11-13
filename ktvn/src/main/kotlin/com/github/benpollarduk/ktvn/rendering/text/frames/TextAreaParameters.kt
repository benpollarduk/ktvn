package com.github.benpollarduk.ktvn.rendering.text.frames

import java.awt.Font

/**
 * Defines parameters for a text area.
 */
public data class TextAreaParameters(
    public val widthInPixels: Int,
    public val availableLines: Int,
    public val font: Font
)
