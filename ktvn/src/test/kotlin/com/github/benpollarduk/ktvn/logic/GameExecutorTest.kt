package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.setup.TestGameConfiguration
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.structure.steps.End.Companion.end
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameExecutorTest {
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
            val visualNovel = VisualNovel.create(story, TestGameConfiguration)
            val game = Game(visualNovel, GameSave.EMPTY, RestorePoint.EMPTY)

            // When
            GameExecutor.execute(game)
        }
    }

    @Test
    fun `given simple game when execute async then no exception thrown`() {
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
            val visualNovel = VisualNovel.create(story, TestGameConfiguration)
            val game = Game(visualNovel, GameSave.EMPTY, RestorePoint.EMPTY)

            // When
            GameExecutor.executeAysnc(game) {
                // nothing
            }
        }
    }

    @Test
    fun `given no game when cancel then no exception thrown`() {
        // Then
        Assertions.assertDoesNotThrow {
            // When
            GameExecutor.cancel()
        }
    }

    @Test
    fun `given simple game when cancel then no exception thrown`() {
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
            val visualNovel = VisualNovel.create(story, TestGameConfiguration)
            val game = Game(visualNovel, GameSave.EMPTY, RestorePoint.EMPTY)

            // When
            GameExecutor.cancel(game)
        }
    }
}
