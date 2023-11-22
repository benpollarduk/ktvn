package com.github.benpollarduk.ktvn.example.chapters.prologue

import com.github.benpollarduk.ktvn.example.chapters.prologue.scenes.introduction
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter

internal fun prologue(): Chapter {
    return chapter {
        it name "Prologue"
        it add introduction()
    }
}
