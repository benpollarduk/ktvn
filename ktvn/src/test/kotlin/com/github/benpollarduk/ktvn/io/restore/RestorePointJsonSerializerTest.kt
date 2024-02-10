package com.github.benpollarduk.ktvn.io.restore

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class RestorePointJsonSerializerTest {
    @Test
    fun `given toFile when temp file then file exists and has content`() {
        // Given
        val restorePoint = RestorePoint(
            "Test",
            emptyMap(),
            StoryRestorePoint.START
        )
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        RestorePointSerializer.toFile(restorePoint, tempFile.absolutePath)

        // Then
        Assertions.assertTrue(tempFile.exists())
        Assertions.assertTrue(tempFile.readBytes().isNotEmpty())
    }

    @Test
    fun `given toFile when invalid path then result is false`() {
        // Given
        val restorePoint = RestorePoint(
            "Test",
            emptyMap(),
            StoryRestorePoint.START
        )
        val path = "abcxyz.jkl"

        // When
        val result = RestorePointSerializer.toFile(restorePoint, path)

        // Then
        Assertions.assertFalse(result.result)
    }

    @Test
    fun `given fromFile when temp file with valid contents then result is true`() {
        // Given
        val restorePoint = RestorePoint(
            "Test",
            emptyMap(),
            StoryRestorePoint.START
        )
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        RestorePointSerializer.toFile(restorePoint, tempFile.absolutePath)
        val result = RestorePointSerializer.fromFile(tempFile.absolutePath)

        // Then
        Assertions.assertTrue(result.result)
    }

    @Test
    fun `given fromFile when no file exists then result is false`() {
        // Given
        val path = "abcxyz.jkl"

        // When
        val result = RestorePointSerializer.fromFile(path)

        // Then
        Assertions.assertFalse(result.result)
    }
}
