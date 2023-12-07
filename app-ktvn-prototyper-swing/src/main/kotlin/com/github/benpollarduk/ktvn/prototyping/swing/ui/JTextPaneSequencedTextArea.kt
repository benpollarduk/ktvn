package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.prototyping.swing.components.SequencedTextArea
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextPane
import javax.swing.SwingUtilities
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument

@Suppress("MagicNumber")
class JTextPaneSequencedTextArea : JPanel(), SequencedTextArea {
    private val header = JLabel().also {
        it.preferredSize = Dimension(0, 30)
        it.font = Font("Arial", Font.ITALIC, 16)
        it.horizontalAlignment = JLabel.LEFT
        it.verticalAlignment = JLabel.CENTER
        it.background = Color.BLACK
    }
    private val textPane = JTextPane().also {
        it.isEditable = false
        it.font = Font("Ariel", Font.PLAIN, 16)
    }
    private val attributes: SimpleAttributeSet = SimpleAttributeSet().also {
        StyleConstants.setLineSpacing(it, 1.0f)
        StyleConstants.setSpaceAbove(it, 0.0f)
    }

    init {
        layout = BorderLayout()
        isOpaque = true

        textPane.isEditable = false
        textPane.background = Color.DARK_GRAY
        textPane.editorKit = JTextPane.createEditorKitForContentType("text/html")
        textPane.contentType = "text/html"
        textPane.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true)

        add(header, BorderLayout.NORTH)
        add(textPane, BorderLayout.CENTER)
    }

    private fun setHeader(value: String) {
        header.text = " $value"
        header.repaint()
    }

    override val areaWidth: Int
        get() = textPane.width

    override val areaHeight: Int
        get() = textPane.height

    override val areaFont: Font
        get() = textPane.font

    override fun clear() {
        textPane.text = ""
    }

    override fun print(char: Char) {
        SwingUtilities.invokeLater {
            val doc: StyledDocument = textPane.styledDocument
            doc.insertString(doc.length, char.toString(), attributes)
        }
    }

    override fun styleFor(character: Character) {
        SwingUtilities.invokeLater {
            StyleConstants.setForeground(attributes, Color.WHITE)
            setHeader(character.name)
        }
    }

    override fun styleFor(narrator: Narrator) {
        SwingUtilities.invokeLater {
            StyleConstants.setForeground(attributes, Color.LIGHT_GRAY)
            setHeader("")
        }
    }

    override fun reset() {
        clear()
        setHeader("")
    }
}
