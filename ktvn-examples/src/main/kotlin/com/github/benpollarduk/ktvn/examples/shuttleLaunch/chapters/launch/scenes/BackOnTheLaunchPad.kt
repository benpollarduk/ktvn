package com.github.benpollarduk.ktvn.examples.shuttleLaunch.chapters.launch.scenes

import com.github.benpollarduk.ktvn.characters.Emotions.happy
import com.github.benpollarduk.ktvn.characters.Emotions.normal
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.audio
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.indefiniteShaking
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.laughing
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.layoutFadeIn
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.layoutSlide
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sceneFadeIn
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sceneFadeOut
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sfxWoosh
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.shuttleDay
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.shuttleDayMusic
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.sophie
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.stop
import com.github.benpollarduk.ktvn.examples.shuttleLaunch.assets.AssetStore.toki
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

internal fun backOnTheLaunchPad(): Scene {
    return scene { scene ->
        scene name "Back on the launch pad"
        scene background shuttleDay
        scene music shuttleDayMusic
        scene transitionIn sceneFadeIn
        scene transitionOut sceneFadeOut
        scene layout createLayout { layout ->
            layout addRightOf sophie
            layout addHidden toki
            layout configure configuration.gameAdapter.layoutAdapter
            layout transition layoutFadeIn
        }
        scene steps listOf(
            next {
                scene.layout moveCenter toki
                toki thinks "I know she doesn't trust me... but I aced basic training and the rest of astronaut school."
            },
            next {
                toki thinks "But I guess she's right, I do act up sometimes."
            },
            next {
                toki thinks "Must be getting near launch... oh man I've lost my watch!"
            },
            next {
                toki thinks "Maybe I left it over here..."
                audio sfx sfxWoosh
                scene.layout transition layoutSlide
                scene.layout moveRight toki
             },
            next {
                toki thinks "...or maybe here"
                audio sfx sfxWoosh
                scene.layout transition layoutSlide
                scene.layout moveLeft toki
            },
            next {
                sophie looks normal
                scene.layout moveRight sophie
                sophie says "There you are, I've been looking everywhere for you! What's up?"
            },
            next {
                toki says "I went to check the time but I lost my watch... just looking for it."
            },
            next {
                sophie looks happy
                sophie begins laughing
                sophie says "Ever the fool."
            },
            next {
                sophie looks normal
                scene.layout exitRight sophie
                toki says "Why does she always call me that?!"
                toki begins indefiniteShaking
            },
            next {
                scene.layout moveRight sophie
                toki begins stop
                sophie says "Because you are."
            },
            next {
                scene.layout exitRight sophie
            },
            next {
                toki says "I should learn to keep quiet."
            },
        )
    }
}
