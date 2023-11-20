package com.github.benpollarduk.ktvn.io.tracking.identifier

import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StepIdentifierTrackerTest {
    @Test
    fun `given empty tracker when register step seen then size is 1`() {
        // Given
        val map = StepIdentifierTracker()

        // When
        map.registerStepSeen(next { })

        // Then
        Assertions.assertEquals(1, map.table.size)
    }

    @Test
    fun `given empty tracker then has been seen is false`() {
        // Given
        val map = StepIdentifierTracker()
        val step = next { }

        // When
        val result = map.hasBeenSeen(step)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given empty tracker when register step seen then has been seen is true`() {
        // Given
        val map = StepIdentifierTracker()
        val step = next { }
        map.registerStepSeen(step)

        // When
        val result = map.hasBeenSeen(step)

        // Then
        Assertions.assertTrue(result)
    }
}
