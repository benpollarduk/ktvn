package com.github.benpollarduk.ktvn.logic.adapters.default

import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter

/**
 * Provides a default [AudioAdapter] with a specified [gameEngine].
 */
internal class DefaultAudioAdapter(private val gameEngine: GameEngine) : AudioAdapter {
    override val audioListener: AudioListener = object : AudioListener {
        override fun sfx(soundEffect: SoundEffect) {
            gameEngine.playSoundEffect(soundEffect)
        }
    }
}
