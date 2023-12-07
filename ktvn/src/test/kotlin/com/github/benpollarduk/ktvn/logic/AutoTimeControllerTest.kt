package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.structure.CancellationToken
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AutoTimeControllerTest {
    @Test
    fun `given auto when wait and not cancelled then return true`() {
        // Given
        val autoTimeController = AutoTimeController()

        // When
        val result = autoTimeController.wait(1000, CancellationToken())

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given auto when wait and cancelled then return false`() {
        // Given
        val autoTimeController = AutoTimeController()
        val token = CancellationToken()
        token.cancel()

        // When
        val result = autoTimeController.wait(1000, token)

        // Then
        Assertions.assertFalse(result)
    }
}
