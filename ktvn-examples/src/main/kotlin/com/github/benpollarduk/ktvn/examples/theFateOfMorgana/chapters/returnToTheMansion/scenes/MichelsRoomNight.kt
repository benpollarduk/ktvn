package com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes

import com.github.benpollarduk.ktvn.characters.Emotions.amused
import com.github.benpollarduk.ktvn.characters.Emotions.disappointed
import com.github.benpollarduk.ktvn.characters.Emotions.normal
import com.github.benpollarduk.ktvn.characters.Emotions.sleepy
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.michel
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.morgana
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.narrator
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.StepResult
import com.github.benpollarduk.ktvn.structure.steps.Conditional.Companion.conditional
import com.github.benpollarduk.ktvn.structure.steps.Decision.Companion.decision
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

@Suppress("LongMethod", "MaxLineLength")
internal fun michelsRoomNight(): Scene {
    return scene { scene ->
        scene name "Michel's Room (Night)"
        scene background "michels-room-night"
        scene music "mansion-theme-night"
        scene layout createLayout { layout ->
            layout addCenter michel
            layout addRightOf morgana
            layout configure configuration.gameAdapter.layoutAdapter
        }
        scene steps listOf(
            next {
                michel looks normal
                narrator narrates "Michel returned to his chambers. As night came down he thought about the witch."
            },
            next { narrator narrates "How did she become so vile? Surely her backstory must be a loathsome tale filled with pain and hatred?" },
            next { narrator narrates "It was approaching midnight and Michel felt weary." },
            decision { decision ->
                decision name "Candle status"
                decision does {
                    narrator asks question { question ->
                        question line "Should Michel blow out his candle and retire for the night?"
                        question option answer { answer ->
                            answer line "Yes"
                            answer does {
                                it.setTrue("Blew out candle")
                            }
                        }
                        question option answer { answer ->
                            answer line "No"
                            answer does {
                                it.setTrue("Left candle lit")
                            }
                        }
                    }
                }
            },
            conditional {
                it condition "Blew out candle"
                it does {
                    michel looks sleepy
                    michel says "Time for bed."
                    narrator narrates "Michel soon fell into a deep sleep."
                }
                it returns StepResult.SelectScene("Michel's Room (Morning)")
            },
            next { michel says "Why rush to bed?" },
            next {
                scene.layout moveRight morgana
                morgana looks normal
                morgana says "Can't sleep dear? Why, do you suffer from insomnia? I would have thought the long days in this mansion would have bored you in to a state of constant slumber!"
                morgana looks amused
            },
            next {
                michel looks disappointed
                michel says "Morgana, must you always be so disagreeable?"
            },
            next { morgana says "That is an opinion. I find myself agreeable to a tee." },
            next { narrator narrates "Without saying another word Michel blew out the candle and quickly fell in to a deep sleep." }
        )
    }
}
