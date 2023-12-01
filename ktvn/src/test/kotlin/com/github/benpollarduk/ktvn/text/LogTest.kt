package com.github.benpollarduk.ktvn.text

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.setup.TestCharacterAdapter
import com.github.benpollarduk.ktvn.setup.TestNarratorAdapter
import com.github.benpollarduk.ktvn.text.log.Log
import com.github.benpollarduk.ktvn.text.log.LogElement
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class LogTest {
    @Test
    fun `given log with no elements when getting count then return 0`() {
        // Given
        val log = Log()

        // When
        val result = log.count

        // Then
        Assertions.assertEquals(0, result)
    }

    @Test
    fun `given log with 1 elements when getting count then return 1`() {
        // Given
        val log = Log()
        log.add(LogElement.StringLog(""))

        // When
        val result = log.count

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given log with maximum capacity 5 when adding 10 elements then getting count then return 5`() {
        // Given
        val log = Log(5)
        var i = 0
        while (i < 10) {
            log.add(LogElement.StringLog(i.toString()))
            i++
        }

        // When
        val result = log.count

        // Then
        Assertions.assertEquals(5, result)
    }

    @Test
    fun `given log with 5 when getting as array then return array with 5 elements`() {
        // Given
        val log = Log()
        var i = 0
        while (i < 5) {
            log.add(LogElement.StringLog(i.toString()))
            i++
        }

        // When
        val result = log.toArray()

        // Then
        Assertions.assertEquals(5, result.size)
    }

    @Test
    fun `given log containing 3 entries of different types containing the words rabbit dog and hedgehog when calling to string then return string with all words present`() {
        // Given
        val log = Log()
        val narrator = Narrator(TestNarratorAdapter())
        val character = Character("Test", TestCharacterAdapter())
        log.add(LogElement.StringLog("Rabbit"))
        log.add(LogElement.CharacterLog(character, "Dog"))
        log.add(LogElement.NarratorLog(narrator, "Hedgehog"))

        // When
        val result = log.toString()

        // Then
        Assertions.assertTrue(result.contains("Rabbit"))
        Assertions.assertTrue(result.contains("Dog"))
        Assertions.assertTrue(result.contains("Hedgehog"))
    }
}
