package com.github.benpollarduk.ktvn.console.story

import com.github.benpollarduk.ktvn.console.story.chapters.prologue.prologue
import com.github.benpollarduk.ktvn.console.story.chapters.returnToTheMansion.returnToTheMansion
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story

internal fun theFateOfMorgana(): Story {
    return story {
        it name "The Fate of Morgana"
        it add prologue()
        it add returnToTheMansion()
    }
}