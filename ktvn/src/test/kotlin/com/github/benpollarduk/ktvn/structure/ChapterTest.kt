package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ChapterTest {
    @Test
    fun `given chapter when name is test then name is set to test`() {
        // Given
        val chapter = chapter {
            it name "Test"
        }

        // When
        val result = chapter.name

        // Then
        Assertions.assertEquals("Test", result)
    }

    @Test
    fun `given chapter when scenes is set to 1 scene then number of scenes is 1`() {
        // Given
        val chapter = chapter {
            it add scene {
            }
        }

        // When
        val result = chapter.numberOfScenes

        // Then
        Assertions.assertEquals(1, result)
    }
}
