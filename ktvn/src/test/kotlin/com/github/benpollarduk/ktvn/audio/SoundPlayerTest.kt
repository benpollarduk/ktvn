package com.github.benpollarduk.ktvn.audio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SoundPlayerTest {
    @Test
    fun `given stop when not playing then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val player = SoundPlayer()

            // When
            player.stop()
        }
    }

    @Test
    fun `given adjust volume when not playing then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val player = SoundPlayer()

            // When
            player.adjustVolume(VolumeManager.NO_ATTENUATION)
        }
    }

    @Test
    fun `given play from file when invalid path then failure`() {
        // Given
        val player = SoundPlayer()

        // When
        val result = player.playFromFile("ABC", VolumeManager.NO_ATTENUATION)

        // Then
        Assertions.assertFalse(result.wasSuccessful)
    }

    @Test
    fun `given play from resource when invalid key then failure`() {
        // Given
        val player = SoundPlayer()

        // When
        val result = player.playFromResource("ABC", VolumeManager.NO_ATTENUATION)

        // Then
        Assertions.assertFalse(result.wasSuccessful)
    }
}
