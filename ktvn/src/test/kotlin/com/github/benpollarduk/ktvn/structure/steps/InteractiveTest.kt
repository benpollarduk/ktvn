package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.InteractiveComponent
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.StepResult
import com.github.benpollarduk.ktvn.structure.steps.Interactive.Companion.interactive
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class InteractiveTest {
    @Test
    fun `given interactive when name is test then name is set to test`() {
        // Given
        val interactive = interactive {
            this name "Test"
        }

        // When
        val result = interactive.name

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given interactive when invoke then component is called`() {
        // Given
        var called = false
        val component = InteractiveComponent { _, _, _ ->
            called = true
            StepResult.Continue
        }
        val interactive = interactive {
            this element component
        }

        // When
        interactive.invoke(Flags(), CancellationToken())

        // Then
        Assertions.assertTrue(called)
    }
}
