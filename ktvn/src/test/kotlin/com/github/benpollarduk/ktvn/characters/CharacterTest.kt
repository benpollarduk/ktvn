package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.AcknowledgeListener
import com.github.benpollarduk.ktvn.logic.listeners.AnswerListener
import com.github.benpollarduk.ktvn.logic.listeners.AskListener
import com.github.benpollarduk.ktvn.logic.listeners.EmoteListener
import com.github.benpollarduk.ktvn.logic.listeners.SpeakListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterTest {
    private val speakListener = object : SpeakListener {
        override fun invoke(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val emoteListener = object : EmoteListener {
        override fun invoke(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val askListener = object : AskListener {
        override fun invoke(character: Character, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }

        override fun invoke(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
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
    fun `given a character named Test then name is assigned`() {
        // Given
        val character = Character("Test", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // Then
        Assertions.assertEquals("Test", character.name)
    }

    @Test
    fun `given a character when assigning emotion then emotion is assigned`() {
        // Given
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // Conditional
        character looks happy

        // Then
        Assertions.assertEquals(happy, character.emotion)
    }

    @Test
    fun `given a character when says then speaks is called`() {
        // Given
        var called = false
        val speakListener = object : SpeakListener {
            override fun invoke(character: Character, line: String, acknowledgement: AcknowledgeListener) {
                called = true
            }
        }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        character.says("")

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a character when ask then asks is called`() {
        // Given
        var called = false
        val askListener = object : AskListener {
            override fun invoke(character: Character, question: Question, answerListener: AnswerListener): Answer {
                called = true
                return Answer.answer { }
            }

            override fun invoke(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
                return Answer.answer { }
            }
        }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        character.asks(Question.question { })

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a character when looks then emotes is called`() {
        // Given
        var called = false
        val emoteListener = object : EmoteListener {
            override fun invoke(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
                called = true
            }
        }
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        character.looks(happy)

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a character when looks happy then emotion is happy`() {
        // Given
        val character = Character("", speakListener, emoteListener, askListener, acknowledgeListener, acknowledgeListener, answerListener)

        // When
        character.looks(happy)

        // Then
        Assertions.assertEquals(happy, character.emotion)
    }
}
