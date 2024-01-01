package com.github.benpollarduk.ktvn.audio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream

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

    @Test
    fun `given play from file when valid file then true`() {
        // Given
        val player = SoundPlayer()
        val resource = "test-audio.wav"
        val testStream = javaClass.classLoader.getResourceAsStream(resource)
        val tempFile = File.createTempFile("test-audio", ".wav")
        tempFile.deleteOnExit()

        // write the contents of the resource to the temporary file
        testStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }

        // When
        val result = player.playFromFile(tempFile.absolutePath, VolumeManager.FULL_ATTENUATION)

        // Then
        Assertions.assertTrue(result.wasSuccessful)
    }
}
