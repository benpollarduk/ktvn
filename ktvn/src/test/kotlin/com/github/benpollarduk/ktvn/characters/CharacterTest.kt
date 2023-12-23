package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.characters.Emotions.happy
import com.github.benpollarduk.ktvn.characters.animations.AnimateListener
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterTest {
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

    private val configuration: CharacterAdapter = object : CharacterAdapter {
        override val askListener: CharacterAskListener = emptyAskListener
        override val emoteListener: EmoteListener = emptyEmoteListener
        override val animateListener: AnimateListener = emptyAnimateListener
        override val speakListener: SpeakListener = emptySpeakListener
        override val thinkListener: ThinkListener = emptyThinkListener
    }

    @Test
    fun `given a character named Test then name is assigned`() {
        // Given
        val character = Character(
            "Test",
            configuration
        )

        // Then
        Assertions.assertEquals("Test", character.name)
    }

    @Test
    fun `given a character when assigning emotion then emotion is assigned`() {
        // Given
        val character = Character(
            "",
            configuration
        )

        // Conditional
        character looks happy

        // Then
        Assertions.assertEquals(happy, character.emotion)
    }

    @Test
    fun `given a character when says then speaks is called`() {
        // Given
        var called = false

        val configuration: CharacterAdapter = object : CharacterAdapter {
            override val speakListener = object : SpeakListener {
                override fun speak(character: Character, line: String) {
                    called = true
                }
            }
            override val askListener: CharacterAskListener = emptyAskListener
            override val emoteListener: EmoteListener = emptyEmoteListener
            override val animateListener: AnimateListener = emptyAnimateListener
            override val thinkListener: ThinkListener = emptyThinkListener
        }

        val character = Character(
            "",
            configuration
        )

        // When
        character.says("")

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a character when ask then asks is called`() {
        // Given
        var called = false
        val configuration: CharacterAdapter = object : CharacterAdapter {
            override val emoteListener: EmoteListener = emptyEmoteListener
            override val animateListener: AnimateListener = emptyAnimateListener
            override val speakListener: SpeakListener = emptySpeakListener
            override val thinkListener: ThinkListener = emptyThinkListener
            override val askListener = object : CharacterAskListener {
                override fun ask(character: Character, question: Question): Answer {
                    called = true
                    return Answer.answer { }
                }
            }
        }

        val character = Character(
            "",
            configuration
        )

        // When
        character.asks(Question.question { })

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a character when looks then emotes is called`() {
        // Given
        var called = false
        val configuration: CharacterAdapter = object : CharacterAdapter {
            override val askListener: CharacterAskListener = emptyAskListener
            override val animateListener: AnimateListener = emptyAnimateListener
            override val speakListener: SpeakListener = emptySpeakListener
            override val thinkListener: ThinkListener = emptyThinkListener
            override val emoteListener = object : EmoteListener {
                override fun emote(character: Character, emotion: Emotion) {
                    called = true
                }
            }
        }

        val character = Character(
            "",
            configuration
        )

        // When
        character.looks(happy)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a character when looks happy then emotion is happy`() {
        // Given
        val character = Character(
            "",
            configuration
        )

        // When
        character.looks(happy)

        // Then
        Assertions.assertEquals(happy, character.emotion)
    }
}
