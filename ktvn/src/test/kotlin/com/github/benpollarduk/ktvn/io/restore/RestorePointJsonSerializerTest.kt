package com.github.benpollarduk.ktvn.io.restore

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class RestorePointJsonSerializerTest {
    @Test
    fun `given toFile when temp file then file exists and has content`() {
        // Given
        val gameSave = RestorePoint(
            "Test",
            emptyMap(),
            StoryRestorePoint.START
        )
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        RestorePointSerializer.toFile(gameSave, tempFile.absolutePath)

        // Then
        Assertions.assertTrue(tempFile.exists())
        Assertions.assertTrue(tempFile.readBytes().isNotEmpty())
    }

    @Test
    fun `given fromFile when temp file with valid contents then result is true`() {
        // Given
        val gameSave = RestorePoint(
            "Test",
            emptyMap(),
            StoryRestorePoint.START
        )
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        RestorePointSerializer.toFile(gameSave, tempFile.absolutePath)
        val result = RestorePointSerializer.fromFile(tempFile.absolutePath)

        // Then
        Assertions.assertTrue(result.result)
    }
}
