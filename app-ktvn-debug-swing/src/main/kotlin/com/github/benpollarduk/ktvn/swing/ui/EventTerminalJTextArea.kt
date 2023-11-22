package com.github.benpollarduk.ktvn.swing.ui

import com.github.benpollarduk.ktvn.swing.Severity
import com.github.benpollarduk.ktvn.swing.components.EventTerminal
import java.awt.Color
import java.awt.Font
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.swing.JMenuItem
import javax.swing.JPopupMenu
import javax.swing.JScrollPane
import javax.swing.JTextPane
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument

@Suppress("MagicNumber")
public class EventTerminalJTextArea(
    private val colors: Map<Severity, Color> = defaultColors
) : JScrollPane(), EventTerminal {
    private val popupMenu = JPopupMenu().also {
        val clearMenuItem = JMenuItem("Clear")
        clearMenuItem.addActionListener { clear() }
        it.add(clearMenuItem)
    }

    private val textPane = JTextPane().also {
        it.isEditable = false
        it.background = Color.BLACK
        it.font = Font("Monospaced", Font.PLAIN, 12)
        it.componentPopupMenu = popupMenu
    }

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

    init {
        horizontalScrollBarPolicy = HORIZONTAL_SCROLLBAR_AS_NEEDED
        verticalScrollBarPolicy = VERTICAL_SCROLLBAR_ALWAYS
        setViewportView(textPane)
    }

    private fun println(color: Color, value: String) {
        val prefix = if (textPane.text.any()) "\n" else ""
        val time = LocalTime.now()

        val doc: StyledDocument = textPane.styledDocument
        val attr = SimpleAttributeSet()
        StyleConstants.setForeground(attr, color)

        doc.insertString(doc.length, " $prefix${time.format(timeFormatter)}: $value", attr)
        textPane.caretPosition = doc.length
    }

    override fun clear() {
        textPane.text = ""
    }

    override fun println(severity: Severity, value: String) {
        println(colors[severity] ?: Color.WHITE, value)
    }

    public companion object {
        /**
         * Get the default colors to use for different [Severity].
         */
        public val defaultColors: Map<Severity, Color> = mapOf(
            Severity.Info to Color.GRAY,
            Severity.Debug to Color.WHITE,
            Severity.Error to Color.RED
        )
    }
}
