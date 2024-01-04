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

    @Test
    fun `given flags when setting a flag false then flag is false`() {
        // Given
        val flags = Flags()

        // When
        flags setFalse "Test"
        val result = flags["Test"]

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given map when from map then values match map`() {
        // Given
        val map = mapOf(
            "A" to true,
            "B" to false
        )
        val flags = Flags.fromMap(map)

        // When
        val a = flags.getValue("A")
        val b = flags.getValue("B")

        // Then
        Assertions.assertTrue(a)
        Assertions.assertFalse(b)
    }
}
