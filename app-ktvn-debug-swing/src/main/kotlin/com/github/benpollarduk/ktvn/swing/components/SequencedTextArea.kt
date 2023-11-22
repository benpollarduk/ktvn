package com.github.benpollarduk.ktvn.swing.components

import java.awt.Font

/**
 * Provides an interface for a display of sequenced text output.
 */
public interface SequencedTextArea {
    /**
     * Get the width.
     */
    public val areaWidth: Int

    /**
     * Get the height.
     */
    public val areaHeight: Int

    /**
     * Get the font.
     */
    public val areaFont: Font

    /**
     * Clear the text area.
     */
    public fun clear()

    /**
     * Print a [char] at a specified [x] and [y] position.
     */
    public fun print(char: Char, x: Int, y: Int)
}
