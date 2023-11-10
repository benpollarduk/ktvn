package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration

/**
 * Provides a character with a specified [name]. A [configuration] must be specified.
 */
@Suppress("LongParameterList")
public class Character(
    public val name: String,
    private val configuration: CharacterConfiguration
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
        configuration.emoteListener.emote(this, emotion, configuration.emoteAcknowledgementListener)
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        configuration.speakListener.speak(this, line, configuration.speakAcknowledgementListener)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return configuration.askListener.ask(this, question, configuration.answerListener)
    }

    /**
     * Begin animating the character with a specified [animation].
     */
    public infix fun begins(animation: Animation) {
        return configuration.animateListener.animate(this, animation, configuration.animateAcknowledgementListener)
    }
}
