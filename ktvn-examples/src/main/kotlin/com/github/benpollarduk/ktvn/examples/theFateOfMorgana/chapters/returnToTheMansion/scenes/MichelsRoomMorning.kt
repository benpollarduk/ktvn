package com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes

import com.github.benpollarduk.ktvn.characters.Emotions.concerned
import com.github.benpollarduk.ktvn.characters.Emotions.normal
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.michel
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.narrator
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Ending.Companion.default
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.StepResult.Continue
import com.github.benpollarduk.ktvn.structure.steps.Conditional.Companion.conditional
import com.github.benpollarduk.ktvn.structure.steps.End.Companion.end
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

@Suppress("LongMethod", "MaxLineLength")
internal fun michelsRoomMorning(): Scene {
    return scene { scene ->
        scene name "Michel's Room (Morning)"
        scene background "michels-room-morning"
        scene music "mansion-theme"
        scene layout createLayout { layout ->
            layout addCenter michel
            layout configure configuration.gameAdapter.layoutAdapter
        }
        scene steps listOf(
            next {
                michel looks normal
                narrator narrates "Morning came swiftly. Michel was awoken to the sweet sound of birdsong coming through the mansions boarded up windows."
            },
            conditional {
                it condition "Left candle lit"
                it does {
                    michel looks concerned
                    narrator narrates "Memories of Morgana's late night ridicule came back to Michel. He let out a sigh."
                    michel looks normal
                }
                it returns Continue
            },
            next { narrator narrates "Perhaps one day this story will be picked up, but for now we will leave Michel and Morgana in the mansion." },
            end {
                it ending default
            }
        )
    }
}
