package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question

/**
 * Provides an interface for objects that can handle answers.
 */
public interface Answers {
    /**
     * Wait for an answer to a [question]. Returns the provided answer.
     */
    public fun waitFor(question: Question): Answer
}
