package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.example.assets.AssetStore
import com.github.benpollarduk.ktvn.example.chapters.prologue.prologue
import com.github.benpollarduk.ktvn.example.chapters.returnToTheMansion.returnToTheMansion
import com.github.benpollarduk.ktvn.logic.StoryTemplate
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story

public class TheFateOfMorgana : StoryTemplate() {
    override val configuration: GameConfiguration = AssetStore.configuration
    override val story: Story = story {
        it name "The Fate of Morgana"
        it add prologue()
        it add returnToTheMansion()
    }
}
