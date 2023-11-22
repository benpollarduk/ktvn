package com.github.benpollarduk.ktvn.logic.adapters.late

import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter

/**
 * Provides an adapter with a [gameEngine] that can be specified after initialization.
 */
internal class LateGameAdapter(internal var gameEngine: GameEngine? = null) : GameAdapter {
    override val characterAdapter: CharacterAdapter = LateCharacterAdapter(gameEngine)
    override val narratorAdapter: NarratorAdapter = LateNarratorAdapter(gameEngine)
    override val layoutAdapter: LayoutAdapter = LateLayoutAdapter(gameEngine)
    override val storyAdapter: StoryAdapter = LateStoryAdapter(gameEngine)
    override val audioAdapter: AudioAdapter = LateAudioAdapter(gameEngine)
}
