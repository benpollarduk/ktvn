package com.github.benpollarduk.ktvn.logic.adapters.late

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter

/**
 * Provides an [AudioAdapter] with a [gameEngine] that can be specified after initialization.
 */
internal class LateAudioAdapter(internal var gameEngine: GameEngine? = null) : AudioAdapter {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            gameEngine?.playSoundEffect(soundEffect)
        }
    }
}
