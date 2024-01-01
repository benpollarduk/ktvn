package com.github.benpollarduk.ktvn.backgrounds

import com.github.benpollarduk.ktvn.backgrounds.ColorBackground.Companion.backgroundFromColor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.awt.Color

class ColorBackgroundTest {
    @Test
    fun `given create then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            backgroundFromColor(Color.BLACK)
        }
    }
}
