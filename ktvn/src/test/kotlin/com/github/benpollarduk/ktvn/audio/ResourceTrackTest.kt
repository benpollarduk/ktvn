package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.ResourceTrack.Companion.trackFromResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResourceTrackTest {
    @Test
    fun `given create then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            trackFromResource("")
        }
    }
}
