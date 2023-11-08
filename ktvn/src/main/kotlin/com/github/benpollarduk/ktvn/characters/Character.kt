package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.AcknowledgeListener
import com.github.benpollarduk.ktvn.logic.listeners.AnswerListener
import com.github.benpollarduk.ktvn.logic.listeners.AskListener
import com.github.benpollarduk.ktvn.logic.listeners.EmoteListener
import com.github.benpollarduk.ktvn.logic.listeners.SpeakListener

/**
 * Provides a character with a specified [name]. Listeners for [speakListener], [emoteListener], [askListener], [speakAcknowledgmentListener],
 * [emoteAcknowledgmentListener] and [answerListener] must be specified.
 */
@Suppress("LongParameterList")
public class Character(
    public val name: String,
    private val speakListener: SpeakListener,
    private val emoteListener: EmoteListener,
    private val askListener: AskListener,
    private val speakAcknowledgmentListener: AcknowledgeListener,
    private val emoteAcknowledgmentListener: AcknowledgeListener,
    private val answerListener: AnswerListener
) {
    /**
     * Get the characters current [Emotion].
     */
    public var emotion: Emotion = BaseEmotions.normal
        private set

    /**
     * Set the current [emotion].
     */
    public infix fun looks(emotion: Emotion) {
        this.emotion = emotion
        emoteListener(this, emotion, emoteAcknowledgmentListener)
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        speakListener(this, line, speakAcknowledgmentListener)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return askListener(this, question, answerListener)
    }
}
