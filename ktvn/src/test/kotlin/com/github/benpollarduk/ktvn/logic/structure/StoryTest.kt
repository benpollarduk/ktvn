package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StoryTest {
    @Test
    fun `given story when name is test then name is set to test`() {
        // Given
        val story = story {
            it name "Test"
        }

        // When
        val result = story.name

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given story when chapters is set to 1 scene then number of chapters is 1`() {
        // Given
        val story = story {
            it add chapter {
            }
        }

        // When
        val result = story.numberOfChapters

        // Then
        Assertions.assertEquals(1, result)
    }
}