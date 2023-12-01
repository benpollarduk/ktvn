package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.prototyping.swing.components.AnswerPicker
import java.awt.Component
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

/**
 * Provides a JDialog for picking answers to a question.
 */
class JFrameAnswerPicker : AnswerPicker {
    private fun raiseQuestion(question: Question): Answer {
        val dialog = JDialog()
        dialog.title = "Question"
        dialog.isModal = true

        val panel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            border = EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN)
        }

        val additionalMessage = JLabel(question.line).apply {
            alignmentX = Component.CENTER_ALIGNMENT
        }

        panel.add(additionalMessage)

        val buttonPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            alignmentX = Component.CENTER_ALIGNMENT
        }

        val buttons = question.answers.map { JButton(it.line) }
        var selectedAnswer = ""

        buttons.forEach { button ->
            buttonPanel.add(Box.createVerticalStrut(MARGIN))
            button.addActionListener {
                selectedAnswer = button.text
                dialog.dispose()
            }
            button.alignmentX = Component.CENTER_ALIGNMENT
            buttonPanel.add(button)
        }

        panel.add(buttonPanel)

        dialog.contentPane.add(panel)
        dialog.defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
        dialog.pack()
        dialog.setLocationRelativeTo(null)
        dialog.isVisible = true

        return question.answers.find { it.line == selectedAnswer } ?: question.answers.first()
    }

    override fun getAnswer(question: Question): Answer {
        return raiseQuestion(question)
    }

    private companion object {
        /**
         * Get the margin.
         */
        private const val MARGIN: Int = 10
    }
}
