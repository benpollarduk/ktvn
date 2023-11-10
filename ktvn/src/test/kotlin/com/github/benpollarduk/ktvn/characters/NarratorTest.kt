package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NarratorTest {
    private val narrateListener = object : NarrateListener {
        override fun narrate(line: String, acknowledgement: AcknowledgeListener) {
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
    fun `given a narrator when narrate then narrates is called`() {
        // Given
        var called = false
        val narrateListener = object : NarrateListener {
            override fun narrate(line: String, acknowledgement: AcknowledgeListener) {
                called = true
            }
        }
        val narrator = Narrator(narrateListener, askListener, acknowledgeListener, answerListener)

        // When
        narrator.narrates("")

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a narrator when ask then asks is called`() {
        // Given
        var called = false
        val askListener = object : AskListener {
            override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
                return answer { }
            }

            override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
                called = true
                return answer { }
            }
        }
        val narrator = Narrator(narrateListener, askListener, acknowledgeListener, answerListener)

        // When
        narrator.asks(question { })

        // Then
        Assertions.assertTrue(called)
    }
}
