package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
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
}