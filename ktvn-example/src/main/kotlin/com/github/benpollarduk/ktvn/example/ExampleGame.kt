package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.audio.AudioManager
import com.github.benpollarduk.ktvn.backgrounds.ColorBackground.Companion.emptyBackground
import com.github.benpollarduk.ktvn.characters.Animations.shaking
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotions.amused
import com.github.benpollarduk.ktvn.characters.Emotions.angry
import com.github.benpollarduk.ktvn.characters.Emotions.concerned
import com.github.benpollarduk.ktvn.characters.Emotions.normal
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.io.Save
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Ending.Companion.default
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.SceneType.Narrative
import com.github.benpollarduk.ktvn.logic.structure.StepResult.Continue
import com.github.benpollarduk.ktvn.logic.structure.StepResult.End
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.steps.Conditional.Companion.conditional
import com.github.benpollarduk.ktvn.logic.structure.steps.Decision.Companion.decision
import com.github.benpollarduk.ktvn.logic.structure.steps.End.Companion.end
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next

/**
 * Provides a creator for an example [Game] with a specified [gameConfiguration].
 */
public class ExampleGame(private val gameConfiguration: GameConfiguration) {
    private val audio = AudioManager(gameConfiguration.audioConfiguration)
    private val narrator = Narrator(gameConfiguration.narratorConfiguration)
    private val morgana: Character = Character("Morgana", gameConfiguration.characterConfiguration)
    private val michel: Character = Character("Michel", gameConfiguration.characterConfiguration)

    private fun introduction(): Scene {
        return scene { scene ->
            scene name "Introduction"
            scene background emptyBackground
            scene type Narrative
            scene layout createLayout {
                it configure gameConfiguration.layoutConfiguration
            }
            scene steps listOf(
                next { narrator narrates "Many years have passed since Michel moved into the mansion." },
                next { narrator narrates "Although Michel has remained amicable, the witch, Morgana, has not." }
            )
        }
    }

    @Suppress("LongMethod")
    private fun insideTheMansion(): Scene {
        return scene { scene ->
            scene name "Inside the mansion"
            scene background "mansion-interior"
            scene music "mansion-theme"
            scene layout createLayout { layout ->
                layout addLeftOf michel
                layout addRightOf morgana
                layout configure gameConfiguration.layoutConfiguration
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
                    it returns End(Ending("Michel dies alone.", 1))
                },
                end {
                    it ending default
                }
            )
        }
    }

    /**
     * Create an instance of the example game.
     */
    public fun create(): Game {
        val story = story { story ->
            story add chapter { chapter ->
                chapter name "Return to the mansion"
                chapter add introduction()
                chapter add insideTheMansion()
            }
        }

        return Game(story, gameConfiguration, Save.empty)
    }
}