package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.FileTrack.Companion.trackFromFile
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FileTrackTest {
    @Test
    fun `given create then does not throw exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            trackFromFile("")
        }
    }
}
