package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StepIdentifierMapperTest {
    @Test
    fun `given one chapter with one scene and one step when mapping then first step id is 1-1-1`() {
        // Given
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        then { }
                    )
                }
            }
        }
        val mapper = StepIdentifierMapper()

        // When
        val map = mapper.map(story)
        val result = map[story.getAllChapters().first().getAllScenes().first().getAllSteps().first()]

        // Then
        Assertions.assertEquals("1-1-1", result.toString())
    }

    @Test
    fun `given one chapter with one scene and two steps when mapping then step 2 id is 1-1-2`() {
        // Given
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        then { },
                        then { }
                    )
                }
            }
        }
        val mapper = StepIdentifierMapper()

        // When
        val map = mapper.map(story)
        val result = map[story.getAllChapters().first().getAllScenes().first().getAllSteps().last()]

        // Then
        Assertions.assertEquals("1-1-2", result.toString())
    }

    @Test
    fun `given one chapter with two scenes and one step when mapping then scene 2 step 1 id is  1-2-1`() {
        // Given
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        then { }
                    )
                }
                this add scene {
                    this steps listOf(
                        then { }
                    )
                }
            }
        }
        val mapper = StepIdentifierMapper()

        // When
        val map = mapper.map(story)
        val result = map[story.getAllChapters().first().getAllScenes().last().getAllSteps().first()]

        // Then
        Assertions.assertEquals("1-2-1", result.toString())
    }

    @Test
    fun `given two chapters with one scene and one step when mapping then chapter 2 scene 1 step 1 id is 2-1-1`() {
        // Given
        val story = story {
            this add chapter {
                this add scene {
                    this steps listOf(
                        then { }
                    )
                }
            }
            this add chapter {
                this add scene {
                    this steps listOf(
                        then { }
                    )
                }
            }
        }
        val mapper = StepIdentifierMapper()

        // When
        val map = mapper.map(story)
        val result = map[story.getAllChapters().last().getAllScenes().first().getAllSteps().first()]

        // Then
        Assertions.assertEquals("2-1-1", result.toString())
    }
}
