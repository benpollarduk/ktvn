package com.github.benpollarduk.ktvn.io.game

import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameSaveJsonParserTest {
    @Test
    fun `given empty save when to json then return valid json`() {
        // Given
        val flags = Flags()
        flags["Test"] = true
        val restorePoint = GameSave(456, listOf(Ending("Test", 1)))
        val expected = """{"totalSeconds":456,"endingsReached":[{"name":"Test","number":1}],"version":"1.0.0"}"""

        // When
        val result = GameSaveJsonParser.toJson(restorePoint)

        // Then
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `given valid json when from json then return valid save`() {
        // Given
        val json = """{"totalSeconds":456,"endingsReached":[{"name":"Test","number":1}],"version":"1.0.0"}"""

        // When
        val result = GameSaveJsonParser.fromJson(json)

        // Then
        Assertions.assertEquals(456, result.totalSeconds)
        Assertions.assertEquals("Test", result.endingsReached.first().name)
        Assertions.assertEquals(1, result.endingsReached.first().number)
        Assertions.assertEquals(GameSave.VERSION_1_0_0, result.version)
    }
}
