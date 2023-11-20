package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter

/**
 * Provides a narrator. A [configuration] must be specified.
 */
public class Narrator(private val configuration: NarratorAdapter) {
    /**
     * Narrate a [line].
     */
    public infix fun narrates(line: String) {
        configuration.narrateListener.narrate(this, line, configuration.narrateAcknowledgementListener)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return configuration.askListener.ask(this, question, configuration.answerListener)
    }
}
