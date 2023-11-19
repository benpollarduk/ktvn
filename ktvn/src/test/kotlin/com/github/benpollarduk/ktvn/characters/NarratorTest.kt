package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NarratorTest {
    private val emptyNarrateListener = object : NarrateListener {
        override fun narrate(narrator: Narrator, line: String, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val emptyAskListener = object : NarratorAskListener {
        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }
    }

    private val emptyAcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // nothing
        }
    }

    private val emptyAnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return question.answers.first()
        }
    }

    @Test
    fun `given a narrator when narrate then narrates is called`() {
        // Given
        var called = false
        val configuration: NarratorConfiguration = object : NarratorConfiguration {
            override val narrateAcknowledgementListener: AcknowledgeListener = emptyAcknowledgeListener
            override val answerListener: AnswerListener = emptyAnswerListener
            override val askListener: NarratorAskListener = emptyAskListener
            override val narrateListener: NarrateListener = object : NarrateListener {
                override fun narrate(narrator: Narrator, line: String, acknowledgement: AcknowledgeListener) {
                    called = true
                }
            }
        }
        val narrator = Narrator(configuration)

        // When
        narrator.narrates("")

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given a narrator when ask then asks is called`() {
        // Given
        var called = false
        val configuration: NarratorConfiguration = object : NarratorConfiguration {
            override val narrateAcknowledgementListener: AcknowledgeListener = emptyAcknowledgeListener
            override val answerListener: AnswerListener = emptyAnswerListener
            override val narrateListener: NarrateListener = emptyNarrateListener
            override val askListener: NarratorAskListener = object : NarratorAskListener {
                override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
                    called = true
                    return answer { }
                }
            }
        }

        val narrator = Narrator(configuration)

        // When
        narrator.asks(question { })

        // Then
        Assertions.assertTrue(called)
    }
}
