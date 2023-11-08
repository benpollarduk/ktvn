package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Positions.left
import com.github.benpollarduk.ktvn.layout.Positions.right
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.AcknowledgeListener
import com.github.benpollarduk.ktvn.logic.listeners.AnswerListener
import com.github.benpollarduk.ktvn.logic.listeners.AskListener
import com.github.benpollarduk.ktvn.logic.listeners.EmoteListener
import com.github.benpollarduk.ktvn.logic.listeners.MoveListener
import com.github.benpollarduk.ktvn.logic.listeners.SpeakListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LayoutTest {
    private val speakListener = object : SpeakListener {
        override fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val emoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val askListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }
    }

    private val acknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // nothing
        }
    }

    private val answerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return question.answers.first()
        }
    }

    @Test
    fun `given layout when add character then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        layout.add(character, left)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add left of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        layout.addLeftOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add right of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        layout.addRightOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add above then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        layout.addAbove(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add below then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        layout.addBelow(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when move character then moves is called`() {
        // Given
        var called = false
        val moveListener = object : MoveListener {
            override fun move(
                character: Character,
                fromPosition: Position,
                toPosition: Position,
                acknowledgement: AcknowledgeListener
            ) {
                called = true
            }
        }
        val layout = Layout.createLayout { it setMoves moveListener }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)
        layout.add(character, left)

        // When
        layout.move(character, right)

        // Then
        Assertions.assertTrue(called)
    }
}
