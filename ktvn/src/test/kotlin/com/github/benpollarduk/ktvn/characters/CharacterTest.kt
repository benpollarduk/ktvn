package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.Acknowledges
import com.github.benpollarduk.ktvn.logic.listeners.Answers
import com.github.benpollarduk.ktvn.logic.listeners.Asks
import com.github.benpollarduk.ktvn.logic.listeners.Emotes
import com.github.benpollarduk.ktvn.logic.listeners.Speaks
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterTest {
    private val speaks = object : Speaks {
        override fun invoke(character: Character, line: String, acknowledgement: Acknowledges) {
            // nothing
        }
    }

    private val emotes = object : Emotes {
        override fun invoke(character: Character, emotion: Emotion) {
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
    fun `given a character named Test then name is assigned`() {
        // Given
        val character = Character("Test", speaks, emotes, asks, acknowledges, answers)

        // Then
        Assertions.assertEquals("Test", character.name)
    }

    @Test
    fun `given a character when assigning emotion then emotion is assigned`() {
        // Given
        val character = Character("", speaks, emotes, asks, acknowledges, answers)

        // Conditional
        character looks happy

        // Then
        Assertions.assertEquals(happy, character.emotion)
    }
}
