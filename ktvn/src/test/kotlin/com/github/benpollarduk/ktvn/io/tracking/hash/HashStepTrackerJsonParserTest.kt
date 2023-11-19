package com.github.benpollarduk.ktvn.io.tracking.hash

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HashStepTrackerJsonParserTest {
    @Test
    fun `given valid hash step tracker when to json then return valid json`() {
        // Given
        val hashStepTracker = HashStepTracker(mutableListOf(1))
        val expected = """{"hashTable":[1],"version":"1.0.0"}"""

        // When
        val result = HashStepTrackerJsonParser.toJson(hashStepTracker)

        // Then
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `given valid json when from json then return valid hash step tracker`() {
        // Given
        val json = """{"hashTable":[1],"version":"1.0.0"}"""

        // When
        val result = HashStepTrackerJsonParser.fromJson(json)

        // Then
        Assertions.assertEquals(HashStepTracker.VERSION_1_0_0, result.version)
        Assertions.assertEquals(1, result.hashTable.size)
        Assertions.assertEquals(1, result.hashTable.first())
    }
}
