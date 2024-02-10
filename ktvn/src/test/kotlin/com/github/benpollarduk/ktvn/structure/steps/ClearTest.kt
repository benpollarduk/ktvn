package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.structure.steps.Clear.Companion.clear
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ClearTest {
    @Test
    fun `given then when name is test then name is set to test`() {
        // Given
        val then = clear {
            this name "Test"
        }

        // When
        val result = then.name

        // Then
        Assertions.assertEquals("Test", result)
    }
}
