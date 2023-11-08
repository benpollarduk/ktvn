package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.backgrounds.EmptyBackground
import com.github.benpollarduk.ktvn.characters.BaseEmotions.amused
import com.github.benpollarduk.ktvn.characters.BaseEmotions.angry
import com.github.benpollarduk.ktvn.characters.BaseEmotions.concerned
import com.github.benpollarduk.ktvn.characters.BaseEmotions.normal
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.listeners.ListenerProvider
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.SceneType
import com.github.benpollarduk.ktvn.logic.structure.StepResult
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.steps.Conditional.Companion.conditional
import com.github.benpollarduk.ktvn.logic.structure.steps.Decision.Companion.decision
import com.github.benpollarduk.ktvn.logic.structure.steps.End.Companion.end
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next

/**
 * Provides a creator for an example story.
 */
public class ExampleCreator(private val listeners: ListenerProvider) {
    private val morgana: Character = createCharacter("Morgana")
    private val michel: Character = createCharacter("Michel")
    private val narrator = Narrator(
        listeners.narrateListener,
        listeners.askListener,
        listeners.speaksAcknowledgementListener,
        listeners.answerListener
    )

    private fun createCharacter(name: String): Character {
        return Character(
            name,
            listeners.speakListener,
            listeners.emoteListener,
            listeners.askListener,
            listeners.speaksAcknowledgementListener,
            listeners.emotesAcknowledgementListener,
            listeners.answerListener
        )
    }

    @Suppress("LongMethod")
    private fun introduction(): Scene {
        return scene { scene ->
            scene name "Introduction"
            scene background EmptyBackground()
            scene type SceneType.Narrative
            scene layout createLayout {
                it setMoveAcknowledgments listeners.movesAcknowledgementListener
            }
            scene steps listOf(
                next { narrator narrates "Many years have passed since Michel moved in to the mansion." },
                next { narrator narrates "Although Michel has remained amicable, the witch, Morgana, has not." }
            )
        }
    }

    @Suppress("LongMethod")
    private fun insideTheMansion(): Scene {
        return scene { scene ->
            scene name "Inside the mansion"
            scene background EmptyBackground()
            scene layout createLayout { layout ->
                layout addLeftOf michel
                layout addRightOf morgana
                layout setMoves listeners.moveListener
                layout setMoveAcknowledgments listeners.movesAcknowledgementListener
            }
            scene steps listOf(
                next { scene.layout moveLeft michel },
                next { michel looks normal },
                next { michel says "Morgana, are you there?" },
                next { scene.layout moveRight morgana },
                next { morgana looks angry },
                next { morgana says "Michel, my dear, you will never be rid of me." },
                next { morgana says "The two of us are cursed to spend eternity in this mansion." },
                next { michel looks concerned },
                next { michel says "Perhaps then we should at least try and get along?" },
                next { morgana looks amused },
                decision { decision ->
                    decision name "Michel's feeling towards Morgana"
                    decision does {
                        morgana asks question { question ->
                            question line "Why we do, don't we dear?"
                            question option answer { answer ->
                                answer line "Of course."
                                answer does {
                                    it.setFlag("Michel likes Morgana")
                                }
                            }
                            question option answer { answer ->
                                answer line "I hate you!"
                                answer does {
                                    it.setFlag("Michel hates Morgana")
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
                    it returns StepResult.Continue
                },
                conditional {
                    it condition "Michel hates Morgana"
                    it does {
                        morgana looks angry
                        morgana says "Ever the fool Michel."
                        morgana says "I shall leave you in isolation."
                        scene.layout exitRight morgana
                        narrator narrates "Michel was left in isolation for eternity."
                    }
                    it returns StepResult.End(Ending("Michel dies alone.", 1))
                },
                next { narrator narrates "And that was the end of that!" },
                end {
                    it ending Ending.default
                }
            )
        }
    }

    /**
     * Create an example story.
     */
    public fun create(): Story {
        return story { story ->
            story add chapter { chapter ->
                chapter name "Return to the mansion"
                chapter add introduction()
                chapter add insideTheMansion()
            }
        }
    }
}
