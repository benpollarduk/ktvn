package com.github.benpollarduk.ktvn.logic.adapters.standard

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter

/**
 * Provides a standard [AudioAdapter] with a specified [gameEngine].
 */
internal class StandardAudioAdapter(private val gameEngine: GameEngine) : AudioAdapter {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            gameEngine.playSoundEffect(soundEffect)
        }
    }
}
