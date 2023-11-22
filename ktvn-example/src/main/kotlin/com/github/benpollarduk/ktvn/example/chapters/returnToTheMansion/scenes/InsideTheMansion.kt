package com.github.benpollarduk.ktvn.example.chapters.returnToTheMansion.scenes

import com.github.benpollarduk.ktvn.characters.Animations.shaking
import com.github.benpollarduk.ktvn.characters.Emotions.amused
import com.github.benpollarduk.ktvn.characters.Emotions.angry
import com.github.benpollarduk.ktvn.characters.Emotions.concerned
import com.github.benpollarduk.ktvn.characters.Emotions.normal
import com.github.benpollarduk.ktvn.example.assets.AssetStore.audio
import com.github.benpollarduk.ktvn.example.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.example.assets.AssetStore.michel
import com.github.benpollarduk.ktvn.example.assets.AssetStore.morgana
import com.github.benpollarduk.ktvn.example.assets.AssetStore.narrator
import com.github.benpollarduk.ktvn.layout.Layout
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.StepResult
import com.github.benpollarduk.ktvn.logic.structure.StepResult.Continue
import com.github.benpollarduk.ktvn.logic.structure.steps.Conditional.Companion.conditional
import com.github.benpollarduk.ktvn.logic.structure.steps.Decision.Companion.decision
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next

@Suppress("LongMethod")
internal fun insideTheMansion(): Scene {
    return Scene.scene { scene ->
        scene name "Inside the mansion"
        scene background "mansion-interior"
        scene music "mansion-theme"
        scene layout Layout.createLayout { layout ->
            layout addLeftOf michel
            layout addRightOf morgana
            layout configure configuration.gameAdapter.layoutAdapter
        }
        scene steps listOf(
            next {
                scene.layout moveLeft michel
                michel looks normal
                michel says "Morgana, are you there?"
            },
            next {
                scene.layout moveRight morgana
                morgana looks angry
                morgana says "Michel, my dear, you will never be rid of me."
                morgana says "The two of us are cursed to spend eternity in this mansion."
            },
            next {
                michel looks concerned
                michel says "Perhaps then we should at least try and get along?"
                morgana looks amused
            },
            decision { decision ->
                decision name "Michel's feeling towards Morgana"
                decision does {
                    morgana asks question { question ->
                        question line "Why we do, don't we dear?"
                        question option answer { answer ->
                            answer line "Of course."
                            answer does {
                                it setTrue "Michel likes Morgana"
                            }
                        }
                        question option answer { answer ->
                            answer line "I hate you!"
                            answer does {
                                it setTrue "Michel hates Morgana"
                            }
                        }
                    }
                }
            },
            conditional {
                it condition "Michel likes Morgana"
                it does {
                    morgana looks amused
                    morgana says "Fool, I despise you!"
                }
                it returns Continue
            },
            conditional {
                it condition "Michel hates Morgana"
                it does {
                    morgana looks angry
                    morgana says "Ever the fool Michel."
                    morgana says "I shall leave you in isolation."
                    scene.layout exitRight morgana
                    narrator narrates "Michel was left in isolation for eternity."
                    audio sfx "scream"
                    michel begins shaking
                }
                it returns StepResult.End(Ending("Michel dies alone.", 1))
            }
        )
    }
}
