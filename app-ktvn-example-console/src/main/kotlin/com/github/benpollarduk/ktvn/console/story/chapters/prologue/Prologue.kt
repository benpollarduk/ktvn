package com.github.benpollarduk.ktvn.console.story.chapters.prologue

import com.github.benpollarduk.ktvn.console.story.chapters.prologue.scenes.introduction
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter

internal fun prologue(): Chapter {
    return chapter {
        it name "Prologue"
        it add introduction()
    }
}