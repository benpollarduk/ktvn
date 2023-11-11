package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.AudioType
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration

/**
 * Provides an [AudioConfiguration] for an ANSI console.
 */
internal class AnsiConsoleAudioConfiguration : AudioConfiguration {
    override val audioListener: AudioListener = object : AudioListener {
        override fun play(key: String) {
            println("BGM: $key")
        }

        override fun sfx(key: String) {
            println("SFX: $key")
        }

        override fun stop(type: AudioType) {
            println("Stop: $type")
        }
    }
}
