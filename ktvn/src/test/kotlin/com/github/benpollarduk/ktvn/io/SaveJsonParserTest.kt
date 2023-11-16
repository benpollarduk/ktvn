package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class SaveJsonParserTest {
    @Test
    fun `given empty save when to json then return valid json`() {
        // Given
        val flags = Flags()
        flags["Test"] = true
        val save = Save("Test", flags.toMap(), StoryRestorePoint.start, 456, listOf(Ending("Test", 1)))
        val expected = """{"name":"Test","flags":{"Test":true},"storyRestorePoint":{"chapterRestorePoint":{"sceneRestorePoint":{"characterRestorePoints":[],"step":0},"scene":0},"chapter":0},"totalSeconds":456,"endingsReached":[{"name":"Test","number":1}],"version":"1.0.0"}"""

        // When
        val result = SaveJsonParser.toJson(save)

        // Then
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `given valid json when from json then return valid save`() {
        // Given
        val json = """{"name":"Test","flags":{"Test":true},"storyRestorePoint":{"chapterRestorePoint":{"sceneRestorePoint":{"characterRestorePoints":[],"step":1},"scene":2},"chapter":3},"totalSeconds":456,"endingsReached":[{"name":"Test","number":1}],"version":"1.0.0"}"""

        // When
        val result = SaveJsonParser.fromJson(json)

        // Then
        Assertions.assertEquals("Test", result.name)
        Assertions.assertEquals(true, result.flags["Test"])
        Assertions.assertEquals(1, result.storyRestorePoint.chapterRestorePoint.sceneRestorePoint.step)
        Assertions.assertEquals(2, result.storyRestorePoint.chapterRestorePoint.scene)
        Assertions.assertEquals(3, result.storyRestorePoint.chapter)
        Assertions.assertEquals(456, result.totalSeconds)
        Assertions.assertEquals("Test", result.endingsReached.first().name)
        Assertions.assertEquals(1, result.endingsReached.first().number)
        Assertions.assertEquals(Save.VERSION_1_0_0, result.version)
    }
}
