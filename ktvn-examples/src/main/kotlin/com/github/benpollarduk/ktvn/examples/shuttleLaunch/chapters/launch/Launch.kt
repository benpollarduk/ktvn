package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch

import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes.backOnTheLaunchPad
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes.memoriesOfToki
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes.onTheLaunchPad
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter

internal fun launch(): Chapter {
    return chapter {
        this name "Chapter 1: Launch"
        this add onTheLaunchPad()
        this add memoriesOfToki()
        this add backOnTheLaunchPad()
    }
}
