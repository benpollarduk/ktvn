package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ThenTest {
    @Test
    fun `given then when name is test then name is set to test`() {
        // Given
        val then = then {
            this name "Test"
        }

        // When
        val result = then.name

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given then when invoke when script set and called`() {
        // Given
        var result = false
        val next = then {
            this does {
                result = true
            }
        }

        // When
        next(cancellationToken = CancellationToken())

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given next when invoke when script set and called`() {
        // Given
        var result = false
        val next = next {
            result = true
        }

        // When
        next(cancellationToken = CancellationToken())

        // Then
        Assertions.assertTrue(result)
    }
}
