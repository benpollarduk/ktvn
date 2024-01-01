package com.github.benpollarduk.ktvn.backgrounds

import com.github.benpollarduk.ktvn.backgrounds.ResourceBackground.Companion.backgroundFromResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResourceBackgroundTest {
    @Test
    fun `given create then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            backgroundFromResource("")
        }
    }
}
