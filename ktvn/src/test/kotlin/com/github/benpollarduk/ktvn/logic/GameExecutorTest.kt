package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.Save
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.steps.End.Companion.end
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameExecutorTest {
    private val chapterListener = object : ChapterListener {
        override fun enter(chapter: Chapter) {
            // nothing
        }

        override fun exit(chapter: Chapter) {
            // nothing
        }
    }
    private val sceneListener = object : SceneListener {
        override fun enter(scene: Scene) {
            // nothing
        }

        override fun exit(scene: Scene) {
            // nothing
        }
    }

    @Test
    fun `given simple game when execute then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val story = story { story ->
                story add chapter { chapter ->
                    chapter add scene { scene ->
                        scene steps listOf(
                            end {
                                it ending Ending.default
                            }
                        )
                    }
                }
            }
            val game = Game(story, Save.empty, chapterListener, sceneListener)

            // When
            GameExecutor.execute(game)
        }
    }
}
