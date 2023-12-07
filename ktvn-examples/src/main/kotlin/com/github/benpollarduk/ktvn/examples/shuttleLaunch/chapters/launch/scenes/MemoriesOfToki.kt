package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes

import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.layoutFadeIn
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sceneFadeIn
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sceneFadeOut
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.shuttleDayMusic
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.shuttleMemory
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sophie
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.tokiMemory
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

@Suppress("MaxLineLength")
internal fun memoriesOfToki(): Scene {
    return scene { scene ->
        scene name "Memory of launch"
        scene background shuttleMemory
        scene music shuttleDayMusic
        scene transitionIn sceneFadeIn
        scene transitionOut sceneFadeOut
        scene layout createLayout { layout ->
            layout addHidden tokiMemory
            layout configure configuration.gameAdapter.layoutAdapter
            layout transition layoutFadeIn
        }
        scene steps listOf(
            next {
                sophie thinks "Things never used to be so complex. However everything changed that fateful night..."
            },
            next {
                scene.layout moveCenter tokiMemory
                sophie thinks "He was always the class clown, but I knew before things went wrong that there was more to him than that."
            },
            next {
                sophie thinks "But things are different now."
            }
        )
    }
}
