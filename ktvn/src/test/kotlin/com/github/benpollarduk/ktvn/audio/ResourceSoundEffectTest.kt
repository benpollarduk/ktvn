package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect.Companion.soundEffectFromResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResourceSoundEffectTest {
    @Test
    fun `given create then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            soundEffectFromResource("")
        }
    }
}
