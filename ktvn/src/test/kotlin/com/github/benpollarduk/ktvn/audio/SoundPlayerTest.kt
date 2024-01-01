package com.github.benpollarduk.ktvn.audio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import kotlin.math.sin

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
    fun `given play when using generated sine wave then return true`() {
        // Given
        val player = SoundPlayer()

        // create dummy audio data (sine wave for testing)
        val sampleRate = 44100.0
        val durationInSeconds = 1.0
        val frequency = 440.0
        val amplitude = 0.5
        val numSamples = (sampleRate * durationInSeconds).toInt()

        // generate sine wave test data
        val audioData = ByteArray(numSamples * 2) // 16-bit samples, so 2 bytes per sample
        val angularFrequency = 2.0 * Math.PI * frequency
        for (i in 0 until numSamples) {
            val sample = (amplitude * sin(angularFrequency * i / sampleRate)).toInt().toShort()
            audioData[i * 2] = sample.toByte()
            audioData[i * 2 + 1] = (sample.toInt() ushr 8).toByte()
        }

        // create a dummy audio stream with a PCM_SIGNED encoding, 44100 Hz, 16-bit, mono
        val dummyAudioFormat = AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            sampleRate.toFloat(),
            16,
            1,
            2,
            sampleRate.toFloat(),
            false
        )
        val dummyAudioStream = AudioInputStream(
            ByteArrayInputStream(audioData),
            dummyAudioFormat,
            audioData.size.toLong()
        )

        // When
        val result = player.play(dummyAudioStream, VolumeManager.FULL_ATTENUATION, false)

        // Then
        Assertions.assertTrue(result.wasSuccessful)
    }
}
