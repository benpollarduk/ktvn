package com.github.benpollarduk.ktvn.example.chapters.prologue.scenes

import com.github.benpollarduk.ktvn.backgrounds.ColorBackground
import com.github.benpollarduk.ktvn.example.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.example.assets.AssetStore.narrator
import com.github.benpollarduk.ktvn.layout.Layout
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneType
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next

internal fun introduction(): Scene {
    return Scene.scene { scene ->
        scene name "Introduction"
        scene background ColorBackground.emptyBackground
        scene type SceneType.Narrative
        scene layout Layout.createLayout {
            it configure configuration.gameAdapter.layoutAdapter
        }
        scene steps listOf(
            next { narrator narrates "Many years have passed since Michel moved into the mansion." },
            next { narrator narrates "Although Michel has remained amicable, the witch, Morgana, has not." }
        )
    }
}