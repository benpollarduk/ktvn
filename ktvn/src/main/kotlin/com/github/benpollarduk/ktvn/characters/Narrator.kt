package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.AcknowledgeListener
import com.github.benpollarduk.ktvn.logic.listeners.AnswerListener
import com.github.benpollarduk.ktvn.logic.listeners.AskListener
import com.github.benpollarduk.ktvn.logic.listeners.NarrateListener

/**
 * Provides a narrator. Listeners for [narrateListener], [askListener], [speakAcknowledgmentListener] and [answerListener] must be specified.
 */
public class Narrator(
    private val narrateListener: NarrateListener,
    private val askListener: AskListener,
    private val speakAcknowledgmentListener: AcknowledgeListener,
    private val answerListener: AnswerListener
) {
    /**
     * Narrate a [line].
     */
    public infix fun narrates(line: String) {
        narrateListener(line, speakAcknowledgmentListener)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return askListener(this, question, answerListener)
    }
}
