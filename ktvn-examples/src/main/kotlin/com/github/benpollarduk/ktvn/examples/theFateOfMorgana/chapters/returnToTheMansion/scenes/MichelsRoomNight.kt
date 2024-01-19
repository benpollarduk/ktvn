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
    return scene {
        this name "Michel's Room (Night)"
        this background "michels-room-night"
        this music "mansion-theme-night"
        this layout createLayout {
            this addCenter michel
            this addRightOf morgana
            this configure configuration.gameAdapter.layoutAdapter
        }
        this steps listOf(
            next {
                michel looks normal
                narrator narrates "Michel returned to his chambers. As night came down he thought about the witch."
            },
            next { narrator narrates "How did she become so vile? Surely her backstory must be a loathsome tale filled with pain and hatred?" },
            next { narrator narrates "It was approaching midnight and Michel felt weary." },
            decision {
                this name "Candle status"
                this does {
                    narrator asks question {
                        this line "Should Michel blow out his candle and retire for the night?"
                        this option answer {
                            this line "Yes"
                            this does {
                                it.setTrue("Blew out candle")
                            }
                        }
                        this option answer {
                            this line "No"
                            this does {
                                it.setTrue("Left candle lit")
                            }
                        }
                    }
                }
            },
            conditional {
                this condition "Blew out candle"
                this does {
                    michel looks sleepy
                    michel says "Time for bed."
                    narrator narrates "Michel soon fell into a deep sleep."
                }
                this returns StepResult.SelectScene("Michel's Room (Morning)")
            },
            next { michel says "Why rush to bed?" },
            next {
                layout moveRight morgana
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
