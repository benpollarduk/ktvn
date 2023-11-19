package com.github.benpollarduk.ktvn.logic.configuration.default

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.configuration.AudioConfiguration

/**
 * Provides a default [AudioConfiguration] with a specified [gameController].
 */
internal class DefaultAudioConfiguration(private val gameController: GameController) : AudioConfiguration {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            gameController.sfx(soundEffect)
        }
    }
}
