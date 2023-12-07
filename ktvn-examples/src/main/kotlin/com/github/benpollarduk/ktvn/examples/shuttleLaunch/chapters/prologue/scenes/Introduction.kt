package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.prologue.scenes

import com.github.benpollarduk.ktvn.audio.NoAudio.Companion.silence
import com.github.benpollarduk.ktvn.backgrounds.ColorBackground.Companion.empty
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.narrator
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.SceneTypes.narrative
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

@Suppress("MaxLineLength")
internal fun introduction(): Scene {
    return scene { scene ->
        scene name "Introduction"
        scene background empty
        scene music silence
        scene type narrative
        scene layout createLayout {
            it configure configuration.gameAdapter.layoutAdapter
        }
        scene steps listOf(
            next { narrator narrates "It's late afternoon and the the shuttle test flight looms ahead." },
            next { narrator narrates "In a few days time the launch sequence will initiate and the shuttle will leave earth for good." },
            next { narrator narrates "Is Toki ready?" }
        )
    }
}
