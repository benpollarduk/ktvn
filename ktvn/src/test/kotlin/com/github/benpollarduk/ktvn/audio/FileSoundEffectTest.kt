package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.FileSoundEffect.Companion.soundEffectFromFile
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FileSoundEffectTest {
    @Test
    fun `given create then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            soundEffectFromFile("")
        }
    }
}
