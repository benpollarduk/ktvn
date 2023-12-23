package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question

/**
 * Provides an interface for listeners to [Narrator] ask events.
 */
public fun interface NarratorAskListener {
    /**
     * Invoke the listener with a specified [narrator] and [question]. Returns the selected answer.
     */
    public fun ask(narrator: Narrator, question: Question): Answer
}
