package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question

/**
 * Provides an interface for listeners to [Character] ask events.
 */
public fun interface CharacterAskListener {
    /**
     * Invoke the listener with a specified [character] and [question]. Returns the selected answer.
     */
    public fun ask(character: Character, question: Question): Answer
}
