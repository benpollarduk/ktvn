package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes

import com.github.benpollarduk.ktvn.audio.NoAudio.Companion.silence
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.shuttleMemory
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sophie
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.tokiMemory
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next

internal fun memoriesOfToki(): Scene {
    return scene { scene ->
        scene name "Memory of launch"
        scene background shuttleMemory
        scene music silence
        scene layout createLayout { layout ->
            layout addRightOf tokiMemory
            layout configure configuration.gameAdapter.layoutAdapter
        }
        scene steps listOf(
            next {
                sophie says "Things didn't used to be so complex. However everything changed that fateful night..."
            },
            next {
                scene.layout moveCenter tokiMemory
                sophie says "Things didn't used to be so complex. However everything changed that fateful night..."
            }
        )
    }
}
