package com.github.benpollarduk.ktvn.logic.adapters.static

import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter

/**
 * Provides an adapter with a specified [gameEngine].
 */
internal class StaticGameAdapter(internal val gameEngine: GameEngine) : GameAdapter {
    override val characterAdapter: CharacterAdapter = StaticCharacterAdapter(gameEngine)
    override val narratorAdapter: NarratorAdapter = StaticNarratorAdapter(gameEngine)
    override val layoutAdapter: LayoutAdapter = StaticLayoutAdapter(gameEngine)
    override val storyAdapter: StoryAdapter = StaticStoryAdapter(gameEngine)
    override val audioAdapter: AudioAdapter = StaticAudioAdapter(gameEngine)
}
