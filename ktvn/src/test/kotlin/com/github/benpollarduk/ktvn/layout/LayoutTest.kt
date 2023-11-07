package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Positions.left
import com.github.benpollarduk.ktvn.layout.Positions.right
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.Acknowledges
import com.github.benpollarduk.ktvn.logic.listeners.Answers
import com.github.benpollarduk.ktvn.logic.listeners.Asks
import com.github.benpollarduk.ktvn.logic.listeners.Emotes
import com.github.benpollarduk.ktvn.logic.listeners.Moves
import com.github.benpollarduk.ktvn.logic.listeners.Speaks
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LayoutTest {
    private val speaks = object : Speaks {
        override fun invoke(character: Character, line: String, acknowledgement: Acknowledges) {
            // nothing
        }
    }

    private val emotes = object : Emotes {
        override fun invoke(character: Character, emotion: Emotion, acknowledgement: Acknowledges) {
            // nothing
        }
    }

    private val asks = object : Asks {
        override fun invoke(character: Character, question: Question, answers: Answers): Answer {
            return question.answers.first()
        }

        override fun invoke(narrator: Narrator, question: Question, answers: Answers): Answer {
            return question.answers.first()
        }
    }

    private val acknowledges = object : Acknowledges {
        override fun waitFor() {
            // nothing
        }
    }

    private val answers = object : Answers {
        override fun waitFor(question: Question): Answer {
            return question.answers.first()
        }
    }

    @Test
    fun `given layout when add character then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speaks, emotes, asks, acknowledges, acknowledges, answers)

        // When
        layout.add(character, left)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add left of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speaks, emotes, asks, acknowledges, acknowledges, answers)

        // When
        layout.addLeftOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add right of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speaks, emotes, asks, acknowledges, acknowledges, answers)

        // When
        layout.addRightOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add above then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speaks, emotes, asks, acknowledges, acknowledges, answers)

        // When
        layout.addAbove(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add below then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character("", speaks, emotes, asks, acknowledges, acknowledges, answers)

        // When
        layout.addBelow(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when move character then moves is called`() {
        // Given
        var called = false
        val moves = object : Moves {
            override fun invoke(
                character: Character,
                fromPosition: Position,
                toPosition: Position,
                acknowledgement: Acknowledges,
            ) {
                called = true
            }
        }
        val layout = Layout.createLayout { it setMoves moves }
        val character = Character("", speaks, emotes, asks, acknowledges, acknowledges, answers)
        layout.add(character, left)

        // When
        layout.move(character, right)

        // Then
        Assertions.assertTrue(called)
    }
}
