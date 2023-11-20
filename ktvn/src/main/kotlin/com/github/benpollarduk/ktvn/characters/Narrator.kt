package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter

/**
 * Provides a narrator. An [adapter] must be specified.
 */
public class Narrator(private val adapter: NarratorAdapter) {
    /**
     * Narrate a [line].
     */
    public infix fun narrates(line: String) {
        adapter.narrateListener.narrate(this, line, adapter.narrateAcknowledgementListener)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return adapter.askListener.ask(this, question, adapter.answerListener)
    }
}
