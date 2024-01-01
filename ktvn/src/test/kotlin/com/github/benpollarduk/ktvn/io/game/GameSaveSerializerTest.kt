package com.github.benpollarduk.ktvn.io.game

import com.github.benpollarduk.ktvn.logic.Ending
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class GameSaveSerializerTest {
    @Test
    fun `given toFile when temp file then file exists and has content`() {
        // Given
        val gameSave = GameSave(
            456,
            listOf(Ending("Test", 1))
        )
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        GameSaveSerializer.toFile(gameSave, tempFile.absolutePath)

        // Then
        Assertions.assertTrue(tempFile.exists())
        Assertions.assertTrue(tempFile.readBytes().isNotEmpty())
    }

    @Test
    fun `given fromFile when temp file with valid contents then result is true`() {
        // Given
        val gameSave = GameSave(
            456,
            listOf(Ending("Test", 1))
        )
        val tempFile = File.createTempFile("test", ".json")
        tempFile.deleteOnExit()

        // When
        GameSaveSerializer.toFile(gameSave, tempFile.absolutePath)
        val result = GameSaveSerializer.fromFile(tempFile.absolutePath)

        // Then
        Assertions.assertTrue(result.result)
    }
}
