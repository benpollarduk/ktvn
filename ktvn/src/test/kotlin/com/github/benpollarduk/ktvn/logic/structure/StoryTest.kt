package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.logic.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.logic.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.logic.structure.steps.Clear.Companion.clear
import com.github.benpollarduk.ktvn.logic.structure.steps.End.Companion.end
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.next
import com.github.benpollarduk.ktvn.logic.structure.steps.Then.Companion.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StoryTest {
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

        override fun clear(scene: Scene) {
            // nothing
        }
    }

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

    @Test
    fun `given story that returns end 0 when begin then return end 0`() {
        // Given
        val story = story { story ->
            story add chapter { chapter ->
                chapter add scene { scene ->
                    scene steps listOf(
                        next { }
                    )
                }
            }
        }

        // When
        val result = story.begin(Flags(), StoryPosition.start, chapterListener, sceneListener, CancellationToken())

        // Then
        Assertions.assertEquals(0, result.number)
    }

    @Test
    fun `given story that returns end 1 when begin then return end 1`() {
        // Given
        val story = story { story ->
            story add chapter { chapter ->
                chapter add scene { scene ->
                    scene steps listOf(
                        next { },
                        end {
                            it ending Ending("", 1)
                        }
                    )
                }
            }
        }

        // When
        val result = story.begin(Flags(), StoryPosition.start, chapterListener, sceneListener, CancellationToken())

        // Then
        Assertions.assertEquals(1, result.number)
    }

    @Test
    fun `given story with one step when begin then chapter enter and exit called`() {
        // Given
        val story = story { story ->
            story add chapter { chapter ->
                chapter add scene { scene ->
                    scene steps listOf(
                        then { }
                    )
                }
            }
        }
        var enterCalled = false
        var exitCalled = false
        val chapterListener = object : ChapterListener {
            override fun enter(chapter: Chapter) {
                enterCalled = true
            }

            override fun exit(chapter: Chapter) {
                exitCalled = true
            }
        }

        // When
        story.begin(Flags(), StoryPosition.start, chapterListener, sceneListener, CancellationToken())

        // Then
        Assertions.assertTrue(enterCalled)
        Assertions.assertTrue(exitCalled)
    }

    @Test
    fun `given story with one step when begin then scene enter and exit called`() {
        // Given
        val story = story { story ->
            story add chapter { chapter ->
                chapter add scene { scene ->
                    scene steps listOf(
                        then { }
                    )
                }
            }
        }
        var enterCalled = false
        var exitCalled = false
        val sceneListener = object : SceneListener {
            override fun enter(scene: Scene) {
                enterCalled = true
            }

            override fun exit(scene: Scene) {
                exitCalled = true
            }

            override fun clear(scene: Scene) {
                // nothing
            }
        }

        // When
        story.begin(Flags(), StoryPosition.start, chapterListener, sceneListener, CancellationToken())

        // Then
        Assertions.assertTrue(enterCalled)
        Assertions.assertTrue(exitCalled)
    }

    @Test
    fun `given story with clear step when begin then clear called`() {
        // Given
        val story = story { story ->
            story add chapter { chapter ->
                chapter add scene { scene ->
                    scene steps listOf(
                        clear { }
                    )
                }
            }
        }
        var clearCalled = false
        val sceneListener = object : SceneListener {
            override fun enter(scene: Scene) {
                // nothing
            }

            override fun exit(scene: Scene) {
                // nothing
            }

            override fun clear(scene: Scene) {
                clearCalled = true
            }
        }

        // When
        story.begin(Flags(), StoryPosition.start, chapterListener, sceneListener, CancellationToken())

        // Then
        Assertions.assertTrue(clearCalled)
    }
}
