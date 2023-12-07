package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch

import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes.backOnTheLaunchPad
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes.memoriesOfToki
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes.onTheLaunchPad
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter

internal fun launch(): Chapter {
    return chapter {
        it name "Chapter 1: Launch"
        it add onTheLaunchPad()
        it add memoriesOfToki()
        it add backOnTheLaunchPad()
    }
}
