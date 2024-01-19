package com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.prologue

import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.prologue.scenes.introduction
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter

internal fun prologue(): Chapter {
    return chapter {
        this name "Prologue"
        this add introduction()
    }
}
