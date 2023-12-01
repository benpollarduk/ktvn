package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NarratorTest {
    private val emptyNarrateListener = object : NarrateListener {
        override fun narrate(narrator: Narrator, line: String) {
            // nothing
        }
    }

    private val emptyAskListener = object : NarratorAskListener {
        override fun ask(narrator: Narrator, question: Question): Answer {
            return question.answers.first()
        }
    }

    @Test
    fun `given a narrator when narrate then narrates is called`() {
        // Given
        var called = false
        val configuration: NarratorAdapter = object : NarratorAdapter {
            override val askListener: NarratorAskListener = emptyAskListener
            override val narrateListener: NarrateListener = object : NarrateListener {
                override fun narrate(narrator: Narrator, line: String) {
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
        val configuration: NarratorAdapter = object : NarratorAdapter {
            override val narrateListener: NarrateListener = emptyNarrateListener
            override val askListener: NarratorAskListener = object : NarratorAskListener {
                override fun ask(narrator: Narrator, question: Question): Answer {
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
