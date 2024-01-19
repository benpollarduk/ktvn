package com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion

import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes.insideTheMansion
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes.michelsRoomMorning
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes.michelsRoomNight
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter

internal fun returnToTheMansion(): Chapter {
    return chapter {
        this name "Chapter 1: Return to the mansion"
        this add insideTheMansion()
        this add michelsRoomNight()
        this add michelsRoomMorning()
    }
}
