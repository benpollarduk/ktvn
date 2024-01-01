package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.steps.Pause.Companion.pause
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PauseTest {
    @Test
    fun `given pause when name is test then name is set to test`() {
        // Given
        val pause = pause {
            it name "Test"
        }

        // When
        val result = pause.name

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given pause when 5 milliseconds then pause of at least 5 milliseconds`() {
        // Given
        val pause = pause {
            it milliseconds 5
        }
        val startTime = System.currentTimeMillis()

        // When
        pause.invoke(Flags(), CancellationToken())
        val endTime = System.currentTimeMillis()

        // Then
        Assertions.assertTrue(endTime - startTime >= 5)
    }

    @Test
    fun `given pause when 1 second then pause of at least 1000 milliseconds`() {
        // Given
        val pause = pause {
            it seconds 1
        }
        val startTime = System.currentTimeMillis()

        // When
        pause.invoke(Flags(), CancellationToken())
        val endTime = System.currentTimeMillis()

        // Then
        Assertions.assertTrue(endTime - startTime >= 1000)
    }
}
