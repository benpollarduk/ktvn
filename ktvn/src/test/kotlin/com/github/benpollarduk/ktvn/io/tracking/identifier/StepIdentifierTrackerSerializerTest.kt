package com.github.benpollarduk.ktvn.io.tracking.identifier

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class StepIdentifierTrackerSerializerTest {
    @Test
    fun `given toFile when temp file then file exists and has content`() {
        // Given
        val stepIdentifierTracker = StepIdentifierTracker(mutableListOf("0.0.1"))
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        StepIdentifierTrackerSerializer.toFile(stepIdentifierTracker, tempFile.absolutePath)

        // Then
        Assertions.assertTrue(tempFile.exists())
        Assertions.assertTrue(tempFile.readBytes().isNotEmpty())
    }

    @Test
    fun `given toFile when invalid path then result is false`() {
        // Given
        val stepIdentifierTracker = StepIdentifierTracker(mutableListOf("0.0.1"))
        val path = "abcxyz.jkl"

        // When
        val result = StepIdentifierTrackerSerializer.toFile(stepIdentifierTracker, path)

        // Then
        Assertions.assertFalse(result.result)
    }

    @Test
    fun `given fromFile when temp file with valid contents then result is true`() {
        // Given
        val stepIdentifierTracker = StepIdentifierTracker(mutableListOf("0.0.1"))
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        StepIdentifierTrackerSerializer.toFile(stepIdentifierTracker, tempFile.absolutePath)
        val result = StepIdentifierTrackerSerializer.fromFile(tempFile.absolutePath)

        // Then
        Assertions.assertTrue(result.result)
    }

    @Test
    fun `given fromFile when no file exists then result is false`() {
        // Given
        val path = "abcxyz.jkl"

        // When
        val result = StepIdentifierTrackerSerializer.fromFile(path)

        // Then
        Assertions.assertFalse(result.result)
    }
}
