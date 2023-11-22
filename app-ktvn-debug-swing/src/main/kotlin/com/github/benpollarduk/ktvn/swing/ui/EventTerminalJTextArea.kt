package com.github.benpollarduk.ktvn.swing.ui

import com.github.benpollarduk.ktvn.swing.Severity
import com.github.benpollarduk.ktvn.swing.components.EventTerminal
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.JTextArea

public class EventTerminalJTextArea(
    private val colors: Map<Severity, Color> = defaultColors
) : JTextArea(), EventTerminal {
    init {
        preferredSize = Dimension(0, 80)
        isEditable = false
        background = Color.BLACK
        foreground = Color.WHITE
        font = Font("Monospaced", Font.PLAIN, 14)

        for (i in 0..10) {
            println(Color.WHITE, "Hello")
        }
    }

    private fun println(color: Color, value: String) {
        append(value + "\n")
        caretPosition = document.length
        setForeground(color)
    }

    override fun clear() {
        text = ""
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