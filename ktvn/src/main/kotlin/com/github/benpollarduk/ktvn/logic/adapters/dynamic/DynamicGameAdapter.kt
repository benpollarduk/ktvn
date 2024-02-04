package com.github.benpollarduk.ktvn.logic.adapters.dynamic

import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter
import com.github.benpollarduk.ktvn.logic.engines.GameEngine

/**
 * Provides an adapter with a [gameEngine] that can be specified after initialization.
 */
internal class DynamicGameAdapter(gameEngine: GameEngine? = null) : GameAdapter {
    private var engine: GameEngine? = null

    /**
     * Get or set the [GameEngine].
     */
    internal var gameEngine: GameEngine?
        get() = engine
        set(value) {
            engine = value
            (characterAdapter as DynamicCharacterAdapter).gameEngine = engine
            (narratorAdapter as DynamicNarratorAdapter).gameEngine = engine
            (layoutAdapter as DynamicLayoutAdapter).gameEngine = engine
            (storyAdapter as DynamicStoryAdapter).gameEngine = engine
            (audioAdapter as DynamicAudioAdapter).gameEngine = engine
        }

    override val characterAdapter: CharacterAdapter = DynamicCharacterAdapter(gameEngine)
    override val narratorAdapter: NarratorAdapter = DynamicNarratorAdapter(gameEngine)
    override val layoutAdapter: LayoutAdapter = DynamicLayoutAdapter(gameEngine)
    override val storyAdapter: StoryAdapter = DynamicStoryAdapter(gameEngine)
    override val audioAdapter: AudioAdapter = DynamicAudioAdapter(gameEngine)
}
