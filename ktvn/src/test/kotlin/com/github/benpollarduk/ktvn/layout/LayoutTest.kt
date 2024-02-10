package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.CharacterAskListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.characters.ThinkListener
import com.github.benpollarduk.ktvn.characters.animations.AnimateListener
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.layout.Positions.left
import com.github.benpollarduk.ktvn.layout.Positions.right
import com.github.benpollarduk.ktvn.layout.transitions.Instant
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
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
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

    @Test
    fun `given layout when move left character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.moveLeft(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when move center character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.moveCenter(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when move right character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.moveRight(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when add left character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.addLeft(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when add left of character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.addLeftOf(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when add right character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.addRight(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when add right of character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.addRightOf(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when exit left character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.exitLeft(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when exit right character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.exitRight(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when exit top character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.exitTop(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when exit bottom character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.exitBottom(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout when disappear character then moves is called`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.disappear(character)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given layout with a character when clear then no characters`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        layout.clear()

        // Then
        Assertions.assertEquals(0, layout.characters)
    }

    @Test
    fun `given layout with a character when to artray of character positions then array with one element returned`() {
        // Given
        var called = false
        val moveListener = MoveListener { _, _, _, _ -> called = true }
        val configuration: LayoutAdapter = object : LayoutAdapter {
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { this configure configuration }
        val character = Character(
            "",
            characterAdapter
        )
        layout.add(character, left)

        // When
        val result = layout.toArrayOfCharacterPosition()

        // Then
        Assertions.assertEquals(1, result.size)
    }
}
