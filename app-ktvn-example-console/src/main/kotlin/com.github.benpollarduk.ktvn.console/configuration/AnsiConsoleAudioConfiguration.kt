package com.github.benpollarduk.ktvn.console.configuration

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.ResourceSoundEffect
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration

/**
 * Provides an [AudioConfiguration] for an ANSI console.
 */
internal class AnsiConsoleAudioConfiguration(
    private val consoleController: AnsiConsoleController
) : AudioConfiguration {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            when (soundEffect) {
                is ResourceSoundEffect -> {
                    consoleController.printlnDirectTemp("Sound effect: ${soundEffect.key}")
                }
                else -> {
                    consoleController.printlnDirectTemp("Sound effect: ${soundEffect}")
                }
            }
        }
    }
}
