package com.github.benpollarduk.ktvn.examples.theFateOfMorgana.chapters.returnToTheMansion.scenes

import com.github.benpollarduk.ktvn.characters.Emotions.amused
import com.github.benpollarduk.ktvn.characters.Emotions.angry
import com.github.benpollarduk.ktvn.characters.Emotions.concerned
import com.github.benpollarduk.ktvn.characters.Emotions.normal
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.audio
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.michel
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.morgana
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.narrator
import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.assets.AssetStore.shaking
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.StepResult.Continue
import com.github.benpollarduk.ktvn.structure.StepResult.End
import com.github.benpollarduk.ktvn.structure.steps.Conditional.Companion.conditional
import com.github.benpollarduk.ktvn.structure.steps.Decision.Companion.decision
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next

@Suppress("LongMethod")
internal fun insideTheMansion(): Scene {
    return scene {
        this name "Inside the mansion"
        this background "mansion-interior"
        this music "mansion-theme"
        this layout createLayout {
            this addLeftOf michel
            this addRightOf morgana
            this configure configuration.gameAdapter.layoutAdapter
        }
        this steps listOf(
            next {
                layout moveLeft michel
                michel looks normal
                michel says "Morgana, are you there?"
            },
            next {
                layout moveRight morgana
                morgana looks angry
                morgana says "Michel, my dear, you will never be rid of me."
                morgana says "The two of us are cursed to spend eternity in this mansion."
            },
            next {
                michel looks concerned
                michel says "Perhaps then we should at least try and get along?"
                morgana looks amused
            },
            decision {
                this name "Michel's feeling towards Morgana"
                this does {
                    morgana asks question {
                        this line "Why we do, don't we dear?"
                        this option answer {
                            this line "Of course."
                            this does {
                                it setTrue "Michel likes Morgana"
                            }
                        }
                        this option answer {
                            this line "I hate you!"
                            this does {
                                it setTrue "Michel hates Morgana"
                            }
                        }
                    }
                }
            },
            conditional {
                this condition "Michel likes Morgana"
                this does {
                    morgana looks amused
                    morgana says "Fool, I despise you!"
                }
                this returns Continue
            },
            conditional {
                this condition "Michel hates Morgana"
                this does {
                    morgana looks angry
                    morgana says "Ever the fool Michel."
                    morgana says "I shall leave you in isolation."
                    layout exitRight morgana
                    narrator narrates "Michel was left in isolation for eternity."
                    audio sfx "scream"
                    michel begins shaking
                }
                this returns End(Ending("Michel dies alone.", 1))
            }
        )
    }
}
