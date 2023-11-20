package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter

/**
 * Provides a character with a specified [name]. An [adapter] must be specified.
 */
public class Character(
    public val name: String,
    private val adapter: CharacterAdapter
) {
    /**
     * Get the characters current [Emotion].
     */
    public var emotion: Emotion = Emotions.normal
        private set

    /**
     * Set the characters [emotion].
     */
    public infix fun looks(emotion: Emotion) {
        this.emotion = emotion
        adapter.emoteListener.emote(this, emotion, adapter.emoteAcknowledgementListener)
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        adapter.speakListener.speak(this, line, adapter.speakAcknowledgementListener)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return adapter.askListener.ask(this, question, adapter.answerListener)
    }

    /**
     * Begin animating the character with a specified [animation].
     */
    public infix fun begins(animation: Animation) {
        return adapter.animateListener.animate(this, animation, adapter.animateAcknowledgementListener)
    }
}
