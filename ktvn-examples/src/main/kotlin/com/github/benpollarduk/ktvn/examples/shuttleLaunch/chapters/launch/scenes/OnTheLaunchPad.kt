package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes

import com.github.benpollarduk.ktvn.characters.Emotions.happy
import com.github.benpollarduk.ktvn.characters.Emotions.normal
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.audio
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.laughing
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.layoutSlide
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sceneFadeIn
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sceneFadeOut
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sfxWoosh
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.shuttleDay
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.shuttleDayMusic
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sophie
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.toki
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

internal fun onTheLaunchPad(): Scene {
    return scene {
        this name "On the launch pad"
        this background shuttleDay
        this music shuttleDayMusic
        this transitionIn sceneFadeIn
        this transitionOut sceneFadeOut
        this layout createLayout {
            this addLeftOf sophie
            this addRightOf toki
            this configure configuration.gameAdapter.layoutAdapter
            this transition layoutSlide
        }
        this steps listOf(
            next {
                layout moveCenter sophie
                sophie looks normal
                sophie says "Where has that fool gotten to now?"
            },
            next { audio sfx sfxWoosh },
            next {
                layout moveLeft sophie
                layout moveRight toki
                toki looks normal
                toki says "Here I am!"
                toki says "Ready for duty!"
            },
            next {
                sophie looks happy
                sophie begins laughing
                sophie says "Ever the fool."
            }
        )
    }
}
