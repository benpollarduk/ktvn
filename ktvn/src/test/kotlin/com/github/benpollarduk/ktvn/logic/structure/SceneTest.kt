package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTracker
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SceneTest {
    @Test
    fun `given scene when name is test then name is set to test`() {
        // Given
        val scene = scene {
            it name "Test"
        }

        // When
        val result = scene.name

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given scene when type is narrative then type is set to narrative`() {
        // Given
        val scene = scene {
            it type SceneType.Narrative
        }

        // When
        val result = scene.type

        // Then
        Assertions.assertEquals(SceneType.Narrative, result)
    }

    @Test
    fun `given scene when layout is set then layout is not null`() {
        // Given
        val scene = scene {
            it layout createLayout { }
        }

        // When
        val result = scene.layout

        // Then
        Assertions.assertNotNull(result)
    }

    @Test
    fun `given scene when steps is set to 1 step then number of steps is 1`() {
        // Given
        val scene = scene {
            it steps listOf(
                then { }
            )
        }

        // When
        val result = scene.numberOfSteps

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given unseen then step and waiting for confirmation when checking can skip step then return false`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        val mode = ProgressionMode.WaitForConfirmation
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given seen then step and waiting for confirmation when checking can skip step then return false`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        tracker.registerStepSeen(step)
        val mode = ProgressionMode.WaitForConfirmation
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given unseen then step and auto when checking can skip step then return false`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        val mode = ProgressionMode.Auto(0)
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given seen then step and auto when checking can skip step then return false`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        tracker.registerStepSeen(step)
        val mode = ProgressionMode.Auto(0)
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given unseen then step and skip when checking can skip step then return false`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        val mode = ProgressionMode.Skip(false)
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given seen then step and skip when checking can skip step then return true`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        tracker.registerStepSeen(step)
        val mode = ProgressionMode.Skip(false)
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given unseen then step and skip with force when checking can skip step then return true`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        val mode = ProgressionMode.Skip(true)
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertTrue(result)
    }

    @Test
    fun `given seen then step and skip with force when checking can skip step then return true`() {
        // Given
        val step = then { }
        val tracker = HashStepTracker()
        tracker.registerStepSeen(step)
        val mode = ProgressionMode.Skip(true)
        val scene = scene {
            it steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker, mode)

        // Then
        Assertions.assertTrue(result)
    }
}
