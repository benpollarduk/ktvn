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

    @Test
    fun `given default when configure then configured`() {
        // Given
        val manager = VolumeManager()
        val configuration = VolumeManagerConfiguration(
            0.0,
            0.1,
            0.2,
            0.3,
            emptyMap(),
            0.4
        )

        // Then
        manager.configure(configuration)

        // When
        Assertions.assertEquals(0.0, manager.getMasterVolume())
        Assertions.assertEquals(0.1, manager.getMusicVolume())
        Assertions.assertEquals(0.2, manager.getSoundEffectVolume())
        Assertions.assertEquals(0.3, manager.getVoiceVolume())
        Assertions.assertEquals(0.4, manager.getCharacterVoiceVolume(Character("ABC", TestCharacterAdapter())))
    }

    @Test
    fun `given specified values when generating a configuration then returned configuration is correct`() {
        // Given
        val manager = VolumeManager()

        // Then
        manager.setMasterVolume(0.0)
        manager.setMusicVolume(0.1)
        manager.setSoundEffectVolume(0.2)
        manager.setVoiceVolume(0.3)
        manager.setOtherVoiceVolume(0.4)
        val result = manager.toConfiguration()

        // When
        Assertions.assertEquals(0.0, result.masterVolume)
        Assertions.assertEquals(0.1, result.musicVolume)
        Assertions.assertEquals(0.2, result.soundEffectVolume)
        Assertions.assertEquals(0.3, result.voiceVolume)
        Assertions.assertEquals(0.4, result.otherVoiceVolume)
    }
}
