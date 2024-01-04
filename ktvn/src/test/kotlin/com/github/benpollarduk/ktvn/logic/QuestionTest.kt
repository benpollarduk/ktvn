package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class QuestionTest {
    @Test
    fun `given question with 1 option when created then 1 answer available`() {
        // Given
        val question = question { question ->
            question line "Test"
            question option answer { answer ->
                answer line "A"
                answer does {
                    it setTrue "B"
                }
            }
        }

        // When
        val result = question.answers.size

        // Then
        Assertions.assertEquals(1, result)
    }
}
