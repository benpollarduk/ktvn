package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question

/**
 * Provides an interface for listeners to ask events.
 */
public interface AskListener {
    /**
     * Invoke the listener with a specified [character] and [question]. Returns the selected answer.
     */
    public fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer

    /**
     * Invoke the listener with a specified [narrator] and [question]. Returns the selected answer.
     */
    public fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer
}
