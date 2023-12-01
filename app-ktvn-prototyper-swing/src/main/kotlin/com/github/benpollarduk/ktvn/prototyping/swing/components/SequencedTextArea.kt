package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import java.awt.Font

/**
 * Provides an interface for a display of sequenced text output.
 */
interface SequencedTextArea {
    /**
     * Get the width.
     */
    val areaWidth: Int

    /**
     * Get the height.
     */
    val areaHeight: Int

    /**
     * Get the font.
     */
    val areaFont: Font

    /**
     * Clear the text area.
     */
    fun clear()

    /**
     * Print a [char].
     */
    fun print(char: Char)

    /**
     * Style for a [character].
     */
    fun styleFor(character: Character)

    /**
     * Style for a [narrator].
     */
    fun styleFor(narrator: Narrator)

    /**
     * Reset.
     */
    fun reset()
}
