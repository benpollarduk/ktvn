package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration

/**
 * Provides an [AudioConfiguration] for an ANSI console.
 */
internal class AnsiConsoleAudioConfiguration : AudioConfiguration {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            println("Sound effect: $soundEffect")
        }
    }
}
