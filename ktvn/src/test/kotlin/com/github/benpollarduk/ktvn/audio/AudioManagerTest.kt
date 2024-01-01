package com.github.benpollarduk.ktvn.audio

import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AudioManagerTest {
    @Test
    fun `given sfx from object when calling sfx then listener invoked`() {
        // Given
        var called = false
        val adapter = object : AudioAdapter {
            override val audioListener: AudioListener = AudioListener {
                called = true
            }
        }
        val manager = AudioManager(adapter)

        // When
        manager.sfx(ResourceSoundEffect(""))

        // Then
        Assertions.assertTrue(called)
    }

    @Test
    fun `given sfx from key when calling sfx then listener invoked`() {
        // Given
        var called = false
        val adapter = object : AudioAdapter {
            override val audioListener: AudioListener = AudioListener {
                called = true
            }
        }
        val manager = AudioManager(adapter)

        // When
        manager.sfx("")

        // Then
        Assertions.assertTrue(called)
    }
}
