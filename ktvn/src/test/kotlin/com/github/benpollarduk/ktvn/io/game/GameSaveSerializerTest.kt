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
    fun `given toFile when invalid path then result is false`() {
        // Given
        val gameSave = GameSave(
            456,
            listOf(Ending("Test", 1))
        )
        val path = "abcxyz.jkl"

        // When
        val result = GameSaveSerializer.toFile(gameSave, path)

        // Then
        Assertions.assertFalse(result.result)
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

    @Test
    fun `given fromFile when no file exists then result is false`() {
        // Given
        val path = "abcxyz.jkl"

        // When
        val result = GameSaveSerializer.fromFile(path)

        // Then
        Assertions.assertFalse(result.result)
    }
}
