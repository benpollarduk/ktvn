package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question

/**
 * Provides an interface for picking answers to questions.
 */
interface AnswerPicker {
    /**
     * Get an [Answer] to a [question].
     */
    fun getAnswer(question: Question): Answer
}
