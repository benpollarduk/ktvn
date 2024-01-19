package com.github.benpollarduk.ktvn.examples.theFateOfMorgana

import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.prologue.prologue
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.returnToTheMansion
import com.github.benpollarduk.ktvn.logic.VisualNovel
import com.github.benpollarduk.ktvn.structure.Story.Companion.story

public class TheFateOfMorgana : VisualNovel(
    story {
        this name "The Fate of Morgana"
        this add prologue()
        this add returnToTheMansion()
    },
    AssetStore.configuration
)
