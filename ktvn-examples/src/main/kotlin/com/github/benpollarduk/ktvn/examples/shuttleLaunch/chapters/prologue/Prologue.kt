package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.prologue

import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.prologue.scenes.introduction
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter

internal fun prologue(): Chapter {
    return chapter {
        it name "Prologue"
        it add introduction()
    }
}
