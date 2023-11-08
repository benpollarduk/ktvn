package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question

/**
 * Provides an interface for listeners to [Question] answer requests.
 */
public interface AnswerListener {
    /**
     * Wait for an answer to a [question]. Returns the provided answer.
     */
    public fun waitFor(question: Question): Answer
}
