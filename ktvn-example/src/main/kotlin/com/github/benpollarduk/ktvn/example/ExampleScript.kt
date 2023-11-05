package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.backgrounds.EmptyBackground
import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import com.github.benpollarduk.ktvn.characters.BaseEmotions.sad
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.listeners.ListenerProvider
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.StepResult
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.StoryPosition
import com.github.benpollarduk.ktvn.logic.structure.steps.Choice.Companion.choice
import com.github.benpollarduk.ktvn.logic.structure.steps.OnlyWhen.Companion.onlyWhen
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.simple

public class ExampleScript(private val listeners: ListenerProvider) {
    private val narrator = Narrator(
        listeners.narrates,
        listeners.asks,
        listeners.acknowledges,
        listeners.answers
    )

    private fun createCharacter(name: String): Character {
        return Character(
            name,
            listeners.speaks,
            listeners.emotes,
            listeners.asks,
            listeners.acknowledges,
            listeners.answers
        )
    }

    @Suppress("LongMethod")
    private fun scene1(): Scene {
        val ben = createCharacter("Ben")
        val beth = createCharacter("Beth")

        return scene { scene ->
            scene name "Scene 1"
            scene background EmptyBackground()
            scene layout createLayout { layout ->
                layout addLeftOf ben
                layout addRightOf beth
                layout setMoves listeners.moves
            }
            scene steps listOf(
                simple { narrator narrates "It was a dark and stormy night..." },
                simple { ben looks sad },
                simple { ben says "Where are you Beth?" },
                simple { scene.layout moveLeft ben },
                simple { scene.layout moveRight beth },
                simple { beth says "I'm here!" },
                simple { ben looks happy },
                simple { beth looks happy },
                simple { narrator narrates "Hours pass..." },
                choice { choice ->
                    choice name "Choice 1"
                    choice does {
                        beth asks question { question ->
                            question line "What's for tea?"
                            question option answer { answer ->
                                answer line "Pasta"
                                answer does {
                                    it.setFlag("pasta for tea")
                                }
                            }
                            question option answer { answer ->
                                answer line "Potato"
                                answer does {
                                    it.setFlag("potato for tea")
                                }
                            }
                        }
                    }
                },
                onlyWhen {
                    it flag "pasta for tea"
                    it does {
                        beth looks sad
                        beth says "Fine, pasta it is"
                    }
                    it returns StepResult.Continue
                },
                onlyWhen {
                    it flag "potato for tea"
                    it does {
                        beth looks happy
                        beth says "Yay, I love potato!"
                    }
                    it returns StepResult.Continue
                },
                simple { narrator narrates "And that was the end of that!" }
            )
        }
    }
    public fun begin(chapterListener: ChapterListener, sceneListener: SceneListener) {
        val story = story { story ->
            story add chapter { chapter ->
                chapter name "Chapter 1"
                chapter add scene1()
            }
        }

        story.begin(Flags.empty, StoryPosition.start, chapterListener, sceneListener)
    }
}
