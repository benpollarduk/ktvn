package com.github.benpollarduk.ktvn.console.story.chapters.returnToTheMansion

import com.github.benpollarduk.ktvn.console.story.chapters.returnToTheMansion.scenes.insideTheMansion
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter

internal fun returnToTheMansion(): Chapter {
    return chapter {
        it name "Chapter 1: Return to the mansion"
        it add insideTheMansion()
    }
}