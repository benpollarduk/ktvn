package com.github.benpollarduk.ktvn.logic.adapters.dynamic

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import com.github.benpollarduk.ktvn.logic.engines.GameEngine

/**
 * Provides an [AudioAdapter] with a [gameEngine] that can be specified after initialization.
 */
internal class DynamicAudioAdapter(internal var gameEngine: GameEngine? = null) : AudioAdapter {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            gameEngine?.playSoundEffect(soundEffect)
        }
    }
}
