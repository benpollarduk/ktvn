package com.github.benpollarduk.ktvn.io.tracking.identifier

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StepIdentifierTrackerJsonParserTest {
    @Test
    fun `given valid step identifier step tracker when to json then return valid json`() {
        // Given
        val stepIdentifierTracker = StepIdentifierTracker(mutableListOf("0.0.1"))
        val expected = """{"table":["0.0.1"],"version":"1.0.0"}"""

        // When
        val result = StepIdentifierTrackerJsonParser.toJson(stepIdentifierTracker)

        // Then
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `given valid json when from json then return valid step identifier step tracker`() {
        // Given
        val json = """{"table":["0.0.1"],"version":"1.0.0"}"""

        // When
        val result = StepIdentifierTrackerJsonParser.fromJson(json)

        // Then
        Assertions.assertEquals(StepIdentifierTracker.VERSION_1_0_0, result.version)
        Assertions.assertEquals(1, result.table.size)
        Assertions.assertEquals("0.0.1", result.table.first())
    }
}
