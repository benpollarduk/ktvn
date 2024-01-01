package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.steps.Decision.Companion.decision
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DecisionTest {
    @Test
    fun `given decision when name is test then name is set to test`() {
        // Given
        val decision = decision {
            it name "Test"
        }

        // When
        val result = decision.name

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given decision when invoke then script called`() {
        // Given
        var called = false
        val decision = decision { decision ->
            decision does {
                called = true
                answer {
                    it line "Test"
                }
            }
        }

        // When
        decision.invoke(Flags(), CancellationToken())

        // Then
        Assertions.assertTrue(called)
    }
}
