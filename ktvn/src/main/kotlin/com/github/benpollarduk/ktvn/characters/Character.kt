package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import org.jetbrains.kotlin.util.prefixIfNot
import org.jetbrains.kotlin.util.suffixIfNot

/**
 * Provides a character with a specified [name]. An [adapter] must be specified. Optionally a string representing the
 * [variation] for this character can be specified.
 */
public class Character(
    public val name: String,
    private val adapter: CharacterAdapter,
    public val variation: String = ""
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
        adapter.emoteListener.emote(this, emotion)
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        var formattedLine = line.prefixIfNot("\"").suffixIfNot("\"")
        adapter.speakListener.speak(this, formattedLine)
    }

    /**
     * Think a [line].
     */
    public infix fun thinks(line: String) {
        adapter.thinkListener.think(this, line)
    }

    /**
     * Ask a [question]. Returns the selected answer.
     */
    public infix fun asks(question: Question): Answer {
        return adapter.askListener.ask(this, question)
    }

    /**
     * Begin animating the character with a specified [animation].
     */
    public infix fun begins(animation: Animation) {
        return adapter.animateListener.animate(this, animation)
    }
}
