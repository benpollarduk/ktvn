package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter

internal class TestAudioAdapter : AudioAdapter {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            // nothing
        }
    }
}
