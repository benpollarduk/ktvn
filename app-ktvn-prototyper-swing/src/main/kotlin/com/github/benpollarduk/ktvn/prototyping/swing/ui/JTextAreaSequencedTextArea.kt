package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.prototyping.swing.components.SequencedTextArea
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.JTextArea

/**
 * Provides a JPanel that provides a text area that can handle sequenced printing.
 */
@Suppress("MagicNumber")
class JTextAreaSequencedTextArea : JPanel(), SequencedTextArea {
    private var header = JLabel().also {
        it.preferredSize = Dimension(0, 30)
        it.font = Font("Ariel", Font.PLAIN, 16)
        it.horizontalAlignment = JLabel.LEFT
        it.verticalAlignment = JLabel.CENTER
    }

    private var textArea = JTextArea().also {
        it.isEditable = false
        it.background = Color.BLACK
        it.foreground = Color.WHITE
        it.font = Font("Ariel", Font.PLAIN, 16)
        it.componentPopupMenu = popupMenu
    }

    private val popupMenu = JPopupMenu().apply {
        val clearMenuItem = JMenuItem("Clear")
        clearMenuItem.addActionListener { clear() }
        add(clearMenuItem)
    }

    init {
        layout = BorderLayout()
        add(header, BorderLayout.NORTH)
        add(textArea, BorderLayout.CENTER)
    }

    override val areaWidth: Int
        get() = width

    override val areaHeight: Int
        get() = height

    override val areaFont: Font
        get() = font

    override fun clear() {
        textArea.text = ""
    }

    override fun print(char: Char) {
        textArea.text += char
    }

    override fun styleFor(character: Character) {
        this.header.text = character.name
    }

    override fun styleFor(narrator: Narrator) {
        this.header.text = ""
    }

    override fun reset() {
        clear()
        this.header.text = ""
    }
}
