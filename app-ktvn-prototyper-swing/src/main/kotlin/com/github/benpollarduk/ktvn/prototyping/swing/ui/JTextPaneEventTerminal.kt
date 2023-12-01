package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.prototyping.swing.Severity
import com.github.benpollarduk.ktvn.prototyping.swing.components.EventTerminal
import org.apache.logging.log4j.kotlin.Logging
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

/**
 * Provides a JPanel that provides an event terminal.
 */
@Suppress("MagicNumber")
class JTextPaneEventTerminal(
    private val colors: Map<Severity, Color> = defaultColors
) : JScrollPane(), Logging, EventTerminal {
    private val popupMenu = JPopupMenu().also {
        val clearMenuItem = JMenuItem("Clear")
        clearMenuItem.addActionListener { clear() }
        it.add(clearMenuItem)
    }

    private val textPane = JTextPane().apply {
        isEditable = false
        background = Color.BLACK
        font = Font("Monospaced", Font.PLAIN, 12)
        componentPopupMenu = popupMenu
    }

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

    init {
        horizontalScrollBarPolicy = HORIZONTAL_SCROLLBAR_AS_NEEDED
        verticalScrollBarPolicy = VERTICAL_SCROLLBAR_ALWAYS
        setViewportView(textPane)
    }

    private fun println(color: Color, value: String) {
        val doc: StyledDocument = textPane.styledDocument
        val attr = SimpleAttributeSet()
        StyleConstants.setForeground(attr, color)
        val prefix = if (textPane.text.any()) "\n" else ""
        val time = LocalTime.now()
        doc.insertString(doc.length, "$prefix${time.format(timeFormatter)}: $value", attr)
        textPane.caretPosition = doc.length
    }

    override fun clear() {
        textPane.text = ""
    }

    override fun println(severity: Severity, value: String) {
        println(colors[severity] ?: Color.WHITE, value)

        when (severity) {
            Severity.INFO -> logger.info(value)
            Severity.DEBUG -> logger.debug(value)
            Severity.ERROR -> logger.error(value)
        }
    }

    companion object {
        /**
         * Get the default colors to use for different [Severity].
         */
        val defaultColors: Map<Severity, Color> = mapOf(
            Severity.INFO to Color.GRAY,
            Severity.DEBUG to Color.WHITE,
            Severity.ERROR to Color.RED
        )
    }
}
