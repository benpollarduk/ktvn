package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a character with a specified [name]. Listeners for [speakListener], [emoteListener], [askListener],
 * [speakAcknowledgmentListener], [emoteAcknowledgmentListener] and [answerListener] must be specified.
 */
@Suppress("LongParameterList")
public class Character(
    public val name: String,
    private val speakListener: SpeakListener,
    private val emoteListener: EmoteListener,
    private val askListener: AskListener,
    private val animateListener: AnimateListener,
    private val speakAcknowledgmentListener: AcknowledgeListener,
    private val emoteAcknowledgmentListener: AcknowledgeListener,
    private val answerListener: AnswerListener,
    private val animateAcknowledgmentListener: AcknowledgeListener
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
        emoteListener.emote(this, emotion, emoteAcknowledgmentListener)
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        speakListener.speak(this, line, speakAcknowledgmentListener)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return askListener.ask(this, question, answerListener)
    }

    /**
     * Begin animating the character with a specified [animation].
     */
    public infix fun begins(animation: Animation) {
        return animateListener.animate(this, animation, animateAcknowledgmentListener)
    }
}
