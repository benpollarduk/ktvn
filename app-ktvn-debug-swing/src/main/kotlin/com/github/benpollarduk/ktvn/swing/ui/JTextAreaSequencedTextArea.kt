package com.github.benpollarduk.ktvn.swing.ui

import com.github.benpollarduk.ktvn.swing.components.SequencedTextArea
import java.awt.Color
import java.awt.Font
import javax.swing.JMenuItem
import javax.swing.JPopupMenu
import javax.swing.JTextArea

/**
 * Provides a text output based on a JTextArea.
 */
@Suppress("MagicNumber")
public class JTextAreaSequencedTextArea : JTextArea(), SequencedTextArea {
    private val popupMenu = JPopupMenu().also {
        val clearMenuItem = JMenuItem("Clear")
        clearMenuItem.addActionListener { clear() }
        it.add(clearMenuItem)
    }

    init {
        isEditable = false
        background = Color.WHITE
        foreground = Color.BLACK
        font = Font("Ariel", Font.PLAIN, 12)
        componentPopupMenu = popupMenu
    }

    override val areaWidth: Int
        get() = width
    override val areaHeight: Int
        get() = height
    override val areaFont: Font
        get() = font

    override fun clear() {
        text = ""
    }

    override fun print(char: Char, x: Int, y: Int) {
        text += char
    }
}
