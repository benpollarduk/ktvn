package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AutoTest {
    @Test
    fun `given auto when wait and not cancelled then return true`() {
        // Given
        val auto = Auto()

        // When
        val result = auto.wait(1000, CancellationToken())

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given auto when wait and cancelled then return false`() {
        // Given
        val auto = Auto()
        val token = CancellationToken()
        token.cancel()

        // When
        val result = auto.wait(1000, token)

        // Then
        Assertions.assertFalse(result)
    }
}
