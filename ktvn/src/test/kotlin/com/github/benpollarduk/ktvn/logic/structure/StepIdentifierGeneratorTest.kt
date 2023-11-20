package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StepIdentifierGeneratorTest {
    @Test
    fun `given one chapter with one scene and one step when calling next then return id 1-1-1`() {
        // Given
        val chapter = chapter { }
        val scene = scene { }
        val generator = StepIdentifierGenerator()

        // When
        val result = generator.next(chapter, scene)

        // Then
        Assertions.assertEquals("1-1-1", result.toString())
    }

    @Test
    fun `given one chapter with one scene and two steps when calling next then return id 1-1-2`() {
        // Given
        val chapter = chapter { }
        val scene = scene { }
        val generator = StepIdentifierGenerator()

        // When
        generator.next(chapter, scene)
        val result = generator.next(chapter, scene)

        // Then
        Assertions.assertEquals("1-1-2", result.toString())
    }

    @Test
    fun `given one chapter with two scenes and one step when calling next then return id 1-2-1`() {
        // Given
        val chapter = chapter { }
        val scene1 = scene { }
        val scene2 = scene { }
        val generator = StepIdentifierGenerator()

        // When
        generator.next(chapter, scene1)
        val result = generator.next(chapter, scene2)

        // Then
        Assertions.assertEquals("1-2-1", result.toString())
    }

    @Test
    fun `given two chapters with one scene and one step when calling next then return id 2-1-1`() {
        // Given
        val chapter1 = chapter { }
        val chapter2 = chapter { }
        val scene1 = scene { }
        val scene2 = scene { }
        val generator = StepIdentifierGenerator()

        // When
        generator.next(chapter1, scene1)
        val result = generator.next(chapter2, scene2)

        // Then
        Assertions.assertEquals("2-1-1", result.toString())
    }
}
