package com.github.benpollarduk.ktvn.logic.adapters.standard

import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter

/**
 * Provides a standard adapter with a specified [gameEngine].
 */
internal class StandardGameAdapter(internal val gameEngine: GameEngine) : GameAdapter {
    override val characterAdapter: CharacterAdapter = StandardCharacterAdapter(gameEngine)
    override val narratorAdapter: NarratorAdapter = StandardNarratorAdapter(gameEngine)
    override val layoutAdapter: LayoutAdapter = StandardLayoutAdapter(gameEngine)
    override val storyAdapter: StoryAdapter = StandardStoryAdapter(gameEngine)
    override val audioAdapter: AudioAdapter = StandardAudioAdapter(gameEngine)
}
