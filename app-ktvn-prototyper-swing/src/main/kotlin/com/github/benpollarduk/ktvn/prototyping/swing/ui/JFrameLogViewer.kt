package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.text.log.Log
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.ScrollPaneConstants
import javax.swing.border.EmptyBorder
import javax.swing.text.DefaultCaret

/**
 * Provides a viewer for a log.
 */
@Suppress("MagicNumber")
class JFrameLogViewer(log: Log) : JFrame("Log Viewer") {
    init {
        val textArea = JTextArea()
        textArea.font = Font("Monospaced", Font.PLAIN, 12)
        textArea.text = log.toString()
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.isEditable = false
        textArea.border = EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN)

        // automatically scroll to the end of the text area
        val caret = textArea.caret as DefaultCaret
        caret.updatePolicy = DefaultCaret.ALWAYS_UPDATE

        val scrollPane = JScrollPane(textArea)
        scrollPane.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        scrollPane.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER

        contentPane.add(scrollPane, BorderLayout.CENTER)
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        size = Dimension(400, 600)
    }

    private companion object {
        /**
         * Get the margin.
         */
        private const val MARGIN: Int = 5
    }
}
