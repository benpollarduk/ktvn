package com.github.benpollarduk.ktvn.examples.shuttleLaunch

import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.launch
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.prologue.prologue
import com.github.benpollarduk.ktvn.io.CharacterResourceLookup
import com.github.benpollarduk.ktvn.io.LazyCharacterResourceLookup
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.logic.VisualNovel
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story

public class ShuttleLaunch : VisualNovel(
    story {
        it name "Shuttle Launch"
        it add prologue()
        it add launch()
    },
    AssetStore.configuration,
    Resolution(RESOLUTION_WIDTH, RESOLUTION_HEIGHT)
) {
    override var characterResourceLookup: CharacterResourceLookup = LazyCharacterResourceLookup(
        "shuttleLaunch/images/characters/",
        ".png"
    )

    private companion object {
        private const val RESOLUTION_WIDTH: Int = 1024
        private const val RESOLUTION_HEIGHT: Int = 768
    }
}
