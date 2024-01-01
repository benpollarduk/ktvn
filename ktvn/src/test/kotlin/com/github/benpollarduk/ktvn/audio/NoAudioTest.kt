package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.audio.NoAudio.Companion.silence
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NoAudioTest {
    @Test
    fun `given silence then loop false`() {
        // Given
        val silence = silence

        // Then
        Assertions.assertFalse(silence.loop)
    }
}
