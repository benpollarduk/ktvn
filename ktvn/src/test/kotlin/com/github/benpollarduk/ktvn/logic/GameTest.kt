package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.Save
import com.github.benpollarduk.ktvn.logic.configuration.console.AnsiConsoleGameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.steps.End.Companion.end
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `given simple game when execute then default ending reached`() {
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
        val game = Game(story, AnsiConsoleGameConfiguration, Save.empty)

        // When
        val result = game.execute()

        // Then
        Assertions.assertEquals(Ending.default, result)
    }

    @Test
    fun `given simple game when saved after execute and return ending 1 then save contains ending`() {
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
        val game = Game(story, AnsiConsoleGameConfiguration, Save.empty)

        // When
        game.execute()
        val result = game.getSave("Test")

        // Then
        Assertions.assertEquals(1, result.endingsReached.size)
    }

    @Test
    fun `given simple game when saved after execute with delay then save has valid total seconds`() {
        // Given
        val story = story { story ->
            story add chapter { chapter ->
                chapter add scene { scene ->
                    scene steps listOf(
                        then {
                            it does {
                                Thread.sleep(1000)
                            }
                        },
                        end {
                            it ending Ending.default
                        }
                    )
                }
            }
        }
        val game = Game(story, AnsiConsoleGameConfiguration, Save.empty)

        // When
        game.execute()
        val result = game.getSave("Test")

        // Then
        Assertions.assertTrue(result.totalSeconds >= 1)
    }
}