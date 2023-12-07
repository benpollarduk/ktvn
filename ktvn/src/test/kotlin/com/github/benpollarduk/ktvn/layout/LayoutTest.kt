package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.CharacterAskListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.characters.ThinkListener
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.layout.Positions.left
import com.github.benpollarduk.ktvn.layout.Positions.right
import com.github.benpollarduk.ktvn.layout.transitions.Instant
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LayoutTest {
    private val emptySpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String) {
            // nothing
        }
    }

    private val emptyThinkListener = object : ThinkListener {
        override fun think(character: Character, line: String) {
            // nothing
        }
    }

    private val emptyEmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion) {
            // nothing
        }
    }

    private val emptyAnimateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation) {
            // nothing
        }
    }

    private val emptyAskListener = object : CharacterAskListener {
        override fun ask(character: Character, question: Question): Answer {
            return question.answers.first()
        }
    }

    private val characterAdapter: CharacterAdapter = object : CharacterAdapter {
        override val askListener: CharacterAskListener = emptyAskListener
        override val emoteListener: EmoteListener = emptyEmoteListener
        override val animateListener: AnimateListener = emptyAnimateListener
        override val speakListener: SpeakListener = emptySpeakListener
        override val thinkListener: ThinkListener = emptyThinkListener
    }

    @Test
    fun `given layout when add character then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterAdapter
        )

        // When
        layout.add(character, left)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add left of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterAdapter
        )

        // When
        layout.addLeftOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add right of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterAdapter
        )

        // When
        layout.addRightOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add above then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterAdapter
        )

        // When
        layout.addAbove(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add below then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterAdapter
        )

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
                transition: LayoutTransition
            ) {
                called = true
            }
        }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { it configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.move(character, right, Instant())

        // Then
        Assertions.assertTrue(called)
    }
}
