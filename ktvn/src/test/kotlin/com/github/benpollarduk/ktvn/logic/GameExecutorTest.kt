package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.configuration.test.TestGameConfiguration
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.steps.End.Companion.end
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
            val game = Game(story, TestGameConfiguration, GameSave.empty, RestorePoint.empty)

            // When
            GameExecutor.execute(game)
        }
    }
}
