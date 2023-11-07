package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.StoryPosition
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class SaveJsonParserTest {
    @Test
    fun `given empty save when to json then return valid json`() {
        // Given
        val flags = Flags()
        flags["Test"] = true
        val save = Save("Test", flags.toMap(), StoryPosition(0, 1, 2), 456, listOf(Ending("Test", 1)))
        val expected = """{"name":"Test","flags":{"Test":true},"position":{"chapter":0,"scene":1,"step":2},"totalSeconds":456,"endingsReached":[{"name":"Test","number":1}]}"""

        // When
        val result = SaveJsonParser.toJson(save)

        // Then
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `given valid json when from json then return valid save`() {
        // Given
        val json = """{"name":"Test","flags":{"Test":true},"position":{"chapter":0,"scene":1,"step":2},"totalSeconds":456,"endingsReached":[{"name":"Test","number":1}]}"""

        // When
        val result = SaveJsonParser.fromJson(json)

        // Then
        Assertions.assertEquals("Test", result.name)
        Assertions.assertEquals(0, result.position.chapter)
        Assertions.assertEquals(1, result.position.scene)
        Assertions.assertEquals(2, result.position.step)
        Assertions.assertEquals(true, result.flags["Test"])
        Assertions.assertEquals(456, result.totalSeconds)
        Assertions.assertEquals("Test", result.endingsReached.first().name)
        Assertions.assertEquals(1, result.endingsReached.first().number)
    }
}
