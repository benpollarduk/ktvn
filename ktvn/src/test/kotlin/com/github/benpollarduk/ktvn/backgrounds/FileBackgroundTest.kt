package com.github.benpollarduk.ktvn.backgrounds

import com.github.benpollarduk.ktvn.backgrounds.FileBackground.Companion.backgroundFromFile
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FileBackgroundTest {
    @Test
    fun `given create then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            backgroundFromFile("")
        }
    }
}
