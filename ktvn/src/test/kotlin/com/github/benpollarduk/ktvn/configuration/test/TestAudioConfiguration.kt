package com.github.benpollarduk.ktvn.configuration.test

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration

internal class TestAudioConfiguration : AudioConfiguration {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            // nothing
        }
    }
}
