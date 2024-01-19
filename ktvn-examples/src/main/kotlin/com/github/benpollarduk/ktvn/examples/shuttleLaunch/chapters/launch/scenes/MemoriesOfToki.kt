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
    return scene {
        this name "Memory of launch"
        this background shuttleMemory
        this music shuttleDayMusic
        this transitionIn sceneFadeIn
        this transitionOut sceneFadeOut
        this layout createLayout {
            this addHidden tokiMemory
            this configure configuration.gameAdapter.layoutAdapter
            this transition layoutFadeIn
        }
        this steps listOf(
            next {
                sophie thinks "Things never used to be so complex. However everything changed that fateful night..."
            },
            next {
                layout moveCenter tokiMemory
                sophie thinks "He was always the class clown, but I knew before things went wrong that there was more to him than that."
            },
            next {
                sophie thinks "But things are different now."
            }
        )
    }
}
