package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTracker
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.SceneTypes.narrative
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SceneTest {
    @Test
    fun `given scene when name is test then name is set to test`() {
        // Given
        val scene = scene {
            this name "Test"
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
            this type narrative
        }

        // When
        val result = scene.type

        // Then
        Assertions.assertEquals(narrative, result)
    }

    @Test
    fun `given scene when layout is set then layout is not null`() {
        // Given
        val scene = scene {
            this layout createLayout { }
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
            this steps listOf(
                then { }
            )
        }

        // When
        val result = scene.numberOfSteps

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given unseen then step when checking can skip step then return false`() {
        // Given
        val step = then { }
        val tracker = StepIdentifierTracker()
        val scene = scene {
            this steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker)

        // Then
        Assertions.assertFalse(result)
    }

    @Test
    fun `given seen then step when checking can skip step then return true`() {
        // Given
        val step = then { }
        val tracker = StepIdentifierTracker()
        tracker.registerStepSeen(step)
        val scene = scene {
            this steps listOf(
                step
            )
        }

        // When
        val result = scene.canSkipStep(step, tracker)

        // Then
        Assertions.assertTrue(result)
    }
}
