package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question

/**
 * Provides an interface for objects that can ask questions.
 */
public interface Asks {
    /**
     * Invoke the listener with a specified [character] and [question]. Returns the selected answer.
     */
    public operator fun invoke(character: Character, question: Question, answers: Answers): Answer

    /**
     * Invoke the listener with a specified [narrator] and [question]. Returns the selected answer.
     */
    public operator fun invoke(narrator: Narrator, question: Question, answers: Answers): Answer
}
