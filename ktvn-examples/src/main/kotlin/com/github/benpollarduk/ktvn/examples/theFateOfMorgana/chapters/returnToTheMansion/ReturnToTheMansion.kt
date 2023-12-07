package com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion

import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes.insideTheMansion
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes.michelsRoomMorning
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes.michelsRoomNight
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter

internal fun returnToTheMansion(): Chapter {
    return chapter {
        it name "Chapter 1: Return to the mansion"
        it add insideTheMansion()
        it add michelsRoomNight()
        it add michelsRoomMorning()
    }
}
