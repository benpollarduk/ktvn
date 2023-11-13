package com.github.benpollarduk.ktvn.rendering.frames

import java.awt.Font

/**
 * Defines parameters for a text frames.
 */
public data class TextFrameParameters(
    public val widthInPixels: Int,
    public val availableLines: Int,
    public val font: Font
)
