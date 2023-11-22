package com.github.benpollarduk.ktvn.swing.example

import com.github.benpollarduk.ktvn.backgrounds.ColorBackground.Companion.emptyBackground
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.adapters.GameAdapter
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next

public fun example(gameAdapter: GameAdapter) :Story {
    val narrator = Narrator(gameAdapter.narratorAdapter)
    return story { story ->
        story name "Test"
        story add chapter { chapter ->
            chapter name "Chapter 1"
            chapter add scene { scene ->
                scene name "Scene 1"
                scene background emptyBackground
                scene steps listOf(
                    next { narrator narrates "Line 1"  },
                    next { narrator narrates "Line 2"  },
                    next { narrator narrates "Line 3"  },
                    next { narrator narrates "Line 4"  },
                    next { narrator narrates "Line 5"  },
                )
            }
        }
    }
}