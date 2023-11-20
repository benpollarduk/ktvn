package com.github.benpollarduk.ktvn.logic.adapters.default

import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter

/**
 * Provides a default adapter with a specified [gameEngine].
 */
internal class DefaultGameAdapter(internal val gameEngine: GameEngine) : GameAdapter {
    override val characterAdapter: CharacterAdapter = DefaultCharacterAdapter(gameEngine)
    override val narratorAdapter: NarratorAdapter = DefaultNarratorAdapter(gameEngine)
    override val layoutAdapter: LayoutAdapter = DefaultLayoutAdapter(gameEngine)
    override val storyAdapter: StoryAdapter = DefaultStoryAdapter(gameEngine)
    override val audioAdapter: AudioAdapter = DefaultAudioAdapter(gameEngine)
}
