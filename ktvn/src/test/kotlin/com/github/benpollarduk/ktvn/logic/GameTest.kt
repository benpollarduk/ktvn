package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.setup.TestGameConfiguration
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.structure.steps.End.Companion.end
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.next
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `given simple game when execute and cancelled then cancelled result returned`() {
        // Given
        var game: Game? = null
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        next {
                            game?.end()
                        }
                    )
                }
            }
        }
        val visualNovel = VisualNovel.create(story, TestGameConfiguration)
        game = Game(visualNovel, GameSave.EMPTY, RestorePoint.EMPTY)

        // When
        val result = game.execute()

        // Then
        Assertions.assertEquals(GameExecutionResult.cancelled, result)
    }

    @Test
    fun `given simple game when execute then default ending reached`() {
        // Given
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        end {
                            this ending Ending.default
                        }
                    )
                }
            }
        }
        val visualNovel = VisualNovel.create(story, TestGameConfiguration)
        val game = Game(visualNovel, GameSave.EMPTY, RestorePoint.EMPTY)

        // When
        val result = game.execute()

        // Then
        Assertions.assertEquals(Ending.default, result.ending)
    }

    @Test
    fun `given simple game when saved after execute and return ending 1 then save contains ending`() {
        // Given
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        end {
                            this ending Ending.default
                        }
                    )
                }
            }
        }
        val visualNovel = VisualNovel.create(story, TestGameConfiguration)
        val game = Game(visualNovel, GameSave.EMPTY, RestorePoint.EMPTY)

        // When
        game.execute()
        val result = game.getGameSave()

        // Then
        Assertions.assertEquals(1, result.endingsReached.size)
    }

    @Test
    fun `given simple game when saved after execute with delay then save has valid total seconds`() {
        // Given
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        then {
                            this does {
                                Thread.sleep(1000)
                            }
                        },
                        end {
                            this ending Ending.default
                        }
                    )
                }
            }
        }
        val visualNovel = VisualNovel.create(story, TestGameConfiguration)
        val game = Game(visualNovel, GameSave.EMPTY, RestorePoint.EMPTY)

        // When
        game.execute()
        val result = game.getGameSave()

        // Then
        Assertions.assertTrue(result.totalSeconds >= 1)
    }
}
