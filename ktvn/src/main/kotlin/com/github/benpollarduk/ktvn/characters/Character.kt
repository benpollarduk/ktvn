package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.Acknowledges
import com.github.benpollarduk.ktvn.logic.listeners.Answers
import com.github.benpollarduk.ktvn.logic.listeners.Asks
import com.github.benpollarduk.ktvn.logic.listeners.Emotes
import com.github.benpollarduk.ktvn.logic.listeners.Speaks

/**
 * Provides a character with a specified [name]. Listeners for [speaks], [emotes], [asks], [speaksAcknowledgment],
 * [emotesAcknowledgment] and [answers] must be specified.
 */
@Suppress("LongParameterList")
public class Character(
    public val name: String,
    private val speaks: Speaks,
    private val emotes: Emotes,
    private val asks: Asks,
    private val speaksAcknowledgment: Acknowledges,
    private val emotesAcknowledgment: Acknowledges,
    private val answers: Answers
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
        emotes(this, emotion, emotesAcknowledgment)
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        speaks(this, line, speaksAcknowledgment)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return asks(this, question, answers)
    }
}
