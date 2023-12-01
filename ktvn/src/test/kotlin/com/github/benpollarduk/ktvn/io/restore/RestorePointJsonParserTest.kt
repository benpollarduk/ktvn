package com.github.benpollarduk.ktvn.io.restore

import com.github.benpollarduk.ktvn.logic.Flags
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@Suppress("MaxLineLength")
class RestorePointJsonParserTest {
    @Test
    fun `given valid restore point when to json then return valid json`() {
        // Given
        val flags = Flags()
        flags["Test"] = true
        val restorePoint = RestorePoint(
            "Test",
            flags.toMap(),
            StoryRestorePoint.START,
            LocalDateTime.MIN
        )
        val expected = """{"name":"Test","flags":{"Test":true},"storyRestorePoint":{"chapterRestorePoint":{"sceneRestorePoint":{"characterRestorePoints":[],"step":1},"scene":1},"chapter":1},"creationDate":[-999999999,1,1,0,0],"thumbnail":{"width":0,"height":0,"rgbBytes":[]},"version":"1.0.0"}"""

        // When
        val result = RestorePointJsonParser.toJson(restorePoint)

        // Then
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `given valid json when from json then return valid restore point`() {
        // Given
        val json = """{"name":"Test","flags":{"Test":true},"storyRestorePoint":{"chapterRestorePoint":{"sceneRestorePoint":{"characterRestorePoints":[],"step":1},"scene":2},"chapter":3},"creationDate":[-999999999,1,1,0,0],"thumbnail":{"width":0,"height":0,"rgbBytes":[]},"version":"1.0.0"}"""

        // When
        val result = RestorePointJsonParser.fromJson(json)

        // Then
        Assertions.assertEquals("Test", result.name)
        Assertions.assertEquals(true, result.flags["Test"])
        Assertions.assertEquals(1, result.storyRestorePoint.chapterRestorePoint.sceneRestorePoint.step)
        Assertions.assertEquals(2, result.storyRestorePoint.chapterRestorePoint.scene)
        Assertions.assertEquals(3, result.storyRestorePoint.chapter)
        Assertions.assertEquals(RestorePoint.VERSION_1_0_0, result.version)
    }
}
