package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.listeners.Acknowledges
import com.github.benpollarduk.ktvn.logic.listeners.Answers
import com.github.benpollarduk.ktvn.logic.listeners.Asks
import com.github.benpollarduk.ktvn.logic.listeners.Narrates
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NarratorTest {
    private val narrates = object : Narrates {
        override fun invoke(line: String, acknowledgement: Acknowledges) {
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
    fun `given a narrator when narrate then narrates is called`() {
        // Given
        var called = false
        val narrates = object : Narrates {
            override fun invoke(line: String, acknowledgement: Acknowledges) {
                called = true
            }
        }
        val narrator = Narrator(narrates, asks, acknowledges, answers)

        // When
        narrator.narrates("")

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a narrator when ask then asks is called`() {
        // Given
        var called = false
        val asks = object : Asks {
            override fun invoke(character: Character, question: Question, answers: Answers): Answer {
                return answer { }
            }

            override fun invoke(narrator: Narrator, question: Question, answers: Answers): Answer {
                called = true
                return answer { }
            }
        }
        val narrator = Narrator(narrates, asks, acknowledges, answers)

        // When
        narrator.asks(question { })

        // Then
        Assertions.assertTrue(called)
    }
}
