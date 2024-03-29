package com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.prologue.scenes

import com.github.benpollarduk.ktvn.backgrounds.ColorBackground.Companion.empty
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.narrator
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.SceneTypes.narrative
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

internal fun introduction(): Scene {
    return scene {
        this name "Introduction"
        this background empty
        this type narrative
        this layout createLayout {
            this configure configuration.gameAdapter.layoutAdapter
        }
        this steps listOf(
            next { narrator narrates "Many years have passed since Michel moved into the mansion." },
            next { narrator narrates "Although Michel has remained amicable, the witch, Morgana, has not." }
        )
    }
}
