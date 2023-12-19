package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.setup.TestCharacterAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class VolumeManagerTest {
    @Test
    fun `given defaults when calculate music volume then return 1p0`() {
        // Given
        val manager = VolumeManager()

        // Then
        val result = manager.calculateMusicVolume()

        // When
        Assertions.assertEquals(1.0, result)
    }

    @Test
    fun `given 0p5 music volume 1p0 master volume when calculate music volume then return 0p5`() {
        // Given
        val manager = VolumeManager()
        manager.setMusicVolume(0.5)

        // Then
        val result = manager.calculateMusicVolume()

        // When
        Assertions.assertEquals(0.5, result)
    }

    @Test
    fun `given 1p0 music volume 0p5 master volume when calculate music volume then return 0p5`() {
        // Given
        val manager = VolumeManager()
        manager.setMasterVolume(0.5)

        // Then
        val result = manager.calculateMusicVolume()

        // When
        Assertions.assertEquals(0.5, result)
    }

    @Test
    fun `given 0p5 music volume 0p5 master volume when calculate music volume then return 0p25`() {
        // Given
        val manager = VolumeManager()
        manager.setMasterVolume(0.5)
        manager.setMusicVolume(0.5)

        // Then
        val result = manager.calculateMusicVolume()

        // When
        Assertions.assertEquals(0.25, result)
    }

    @Test
    fun `given 0p5 sound effect volume 1p0 master volume when calculate sound effect volume then return 0p5`() {
        // Given
        val manager = VolumeManager()
        manager.setSoundEffectVolume(0.5)

        // Then
        val result = manager.calculateSoundEffectVolume()

        // When
        Assertions.assertEquals(0.5, result)
    }

    @Test
    fun `given 1p0 sound effect volume 0p5 master volume when calculate sound effect volume then return 0p5`() {
        // Given
        val manager = VolumeManager()
        manager.setMasterVolume(0.5)

        // Then
        val result = manager.calculateSoundEffectVolume()

        // When
        Assertions.assertEquals(0.5, result)
    }

    @Test
    fun `given 0p5 sound effect volume 0p5 master volume when calculate sound effect volume then return 0p25`() {
        // Given
        val manager = VolumeManager()
        manager.setMasterVolume(0.5)
        manager.setSoundEffectVolume(0.5)

        // Then
        val result = manager.calculateSoundEffectVolume()

        // When
        Assertions.assertEquals(0.25, result)
    }

    @Test
    fun `given 0p5 voice volume 1p0 master volume when calculate voice volume then return 0p5`() {
        // Given
        val manager = VolumeManager()
        manager.setVoiceVolume(0.5)

        // Then
        val result = manager.calculateVoiceVolume(null)

        // When
        Assertions.assertEquals(0.5, result)
    }

    @Test
    fun `given 1p0 voice volume 0p5 master volume when calculate voice volume then return 0p5`() {
        // Given
        val manager = VolumeManager()
        manager.setMasterVolume(0.5)

        // Then
        val result = manager.calculateVoiceVolume(null)

        // When
        Assertions.assertEquals(0.5, result)
    }

    @Test
    fun `given 0p5 voice volume 0p5 master volume when calculate voice volume then return 0p25`() {
        // Given
        val manager = VolumeManager()
        manager.setMasterVolume(0.5)
        manager.setVoiceVolume(0.5)

        // Then
        val result = manager.calculateVoiceVolume(null)

        // When
        Assertions.assertEquals(0.25, result)
    }

    @Test
    fun `given 0p5 character volume 1p0 voice volume 1p0 master volume when calculate voice volume then return 0p5`() {
        // Given
        val manager = VolumeManager()
        val c = Character("", TestCharacterAdapter(), "")
        manager.setCharacterVoiceVolume(c, 0.5)

        // Then
        val result = manager.calculateVoiceVolume(c)

        // When
        Assertions.assertEquals(0.5, result)
    }

    @Test
    fun `given 0p5 character volume 0p5 voice volume 0p5 master volume when calculate voice volume then return 0p125`() {
        // Given
        val manager = VolumeManager()
        val c = Character("", TestCharacterAdapter(), "")
        manager.setMasterVolume(0.5)
        manager.setVoiceVolume(0.5)
        manager.setCharacterVoiceVolume(c, 0.5)

        // Then
        val result = manager.calculateVoiceVolume(c)

        // When
        Assertions.assertEquals(0.125, result)
    }
}
