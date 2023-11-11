package com.github.benpollarduk.ktvn.logic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FlagsTest {
    @Test
    fun `given flags when getting a flag that doesnt exist then return false`() {
        // Given
        val flags = Flags()

        // When
        val result = flags["Test"]

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given flags when getting a flag that does exist then return true`() {
        // Given
        val flags = Flags()

        // When
        flags setTrue "Test"
        val result = flags["Test"]

        // Then
        Assertions.assertTrue(result)
    }
}
