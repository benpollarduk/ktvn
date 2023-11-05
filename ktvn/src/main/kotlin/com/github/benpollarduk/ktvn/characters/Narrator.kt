package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.Acknowledges
import com.github.benpollarduk.ktvn.logic.listeners.Answers
import com.github.benpollarduk.ktvn.logic.listeners.Asks
import com.github.benpollarduk.ktvn.logic.listeners.Narrates

/**
 * Provides a narrator. Listeners for [narrates], [asks], [speaksAcknowledges] and [answers] must be specified.
 */
public class Narrator(
    private val narrates: Narrates,
    private val asks: Asks,
    private val speaksAcknowledgment: Acknowledges,
    private val answers: Answers
) {
    /**
     * Narrate a [line].
     */
    public infix fun narrates(line: String) {
        narrates(line, speaksAcknowledgment)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return asks(this, question, answers)
    }
}
