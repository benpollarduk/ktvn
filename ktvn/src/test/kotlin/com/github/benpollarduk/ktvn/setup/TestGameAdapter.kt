package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.logic.adapters.AudioAdapter
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter

internal object TestGameAdapter : GameAdapter {
    override val characterAdapter: CharacterAdapter = TestCharacterAdapter()
    override val narratorAdapter: NarratorAdapter = TestNarratorAdapter()
    override val layoutAdapter: LayoutAdapter = TestLayoutAdapter()
    override val storyAdapter: StoryAdapter = TestStoryAdapter()
    override val audioAdapter: AudioAdapter = TestAudioAdapter()
}
