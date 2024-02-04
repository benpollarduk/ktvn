package com.github.benpollarduk.ktvn.logic.engines.ansiConsole

import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotions.happy
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.animations.Laugh
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.Question.Companion.question
import com.github.benpollarduk.ktvn.logic.configuration.DynamicGameConfiguration
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Chapter.Companion.chapter
import com.github.benpollarduk.ktvn.structure.ChapterTransitions
import com.github.benpollarduk.ktvn.structure.Scene.Companion.scene
import com.github.benpollarduk.ktvn.structure.Story.Companion.story
import com.github.benpollarduk.ktvn.structure.steps.Then.Companion.then
import com.github.benpollarduk.ktvn.structure.transitions.Instant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class AnsiConsoleGameEngineTest {
    private class TestConsoleAdapter : ConsoleAdapter {
        internal val output = mutableListOf<String>()
        internal var input = ""
        override fun print(line: String) {
            output.add(line)
        }

        override fun readln(): String {
            return input
        }
    }

    @Test
    fun `Given character when speaks then adapter output has some entries`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)

        // When
        thread { engine.beginProcessingInput() }
        character.says("Test")
        engine.endProcessingInput()

        // Then
        Assertions.assertTrue(adapter.output.any())
    }

    @Test
    fun `Given character thinks then adapter output has some entries`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)

        // When
        thread { engine.beginProcessingInput() }
        character.thinks("Test")
        engine.endProcessingInput()

        // Then
        Assertions.assertTrue(adapter.output.any())
    }

    @Test
    fun `Given character when asks question with one answer then first returned`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)
        val question = question {
            this line "Test"
            this option answer {
                this line ""
            }
        }

        // When
        adapter.input = "1"
        thread { engine.beginProcessingInput() }
        val result = character.asks(question)
        engine.endProcessingInput()

        // Then
        Assertions.assertEquals(question.answers.first(), result)
    }

    @Test
    fun `Given character when emotes then adapter output has some entries`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)

        // When
        thread { engine.beginProcessingInput() }
        character.looks(happy)
        engine.endProcessingInput()

        // Then
        Assertions.assertTrue(adapter.output.any())
    }

    @Test
    fun `Given character when animates then adapter output has some entries`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)

        // When
        thread { engine.beginProcessingInput() }
        character.begins(Laugh(10.0, 10, 10))
        engine.endProcessingInput()

        // Then
        Assertions.assertTrue(adapter.output.any())
    }

    @Test
    fun `Given character when moves left then adapter output has some entries`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)
        val layout = createLayout {
            this configure configuration.gameAdapter.layoutAdapter
        }

        // When
        thread { engine.beginProcessingInput() }
        layout.moveLeft(character)
        engine.endProcessingInput()

        // Then
        Assertions.assertTrue(adapter.output.any())
    }

    @Test
    fun `Given narrator when narrates then adapter output has some entries`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val narrator = Narrator(configuration.gameAdapter.narratorAdapter)

        // / When
        thread { engine.beginProcessingInput() }
        narrator.narrates("Test")
        engine.endProcessingInput()

        Assertions.assertTrue(adapter.output.any())
    }

    @Test
    fun `Given narrator when asks question with one answer then first returned`() {
        // Given
        val adapter = TestConsoleAdapter()
        val engine = AnsiConsoleGameEngine(adapter = adapter)
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val narrator = Narrator(configuration.gameAdapter.narratorAdapter)
        val question = question {
            this line "Test"
            this option answer {
                this line ""
            }
        }

        // When
        thread { engine.beginProcessingInput() }
        adapter.input = "1"
        val result = narrator.asks(question)
        engine.endProcessingInput()

        // Then
        Assertions.assertEquals(question.answers.first(), result)
    }

    @Test
    fun `Given clear scene then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine

            // When
            engine.clearScene(scene { })
        }
    }

    @Test
    fun `Given play sound effect then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val sfx = object : SoundEffect { }

            // When
            engine.playSoundEffect(sfx)
        }
    }

    @Test
    fun `Given enter story then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val story = story {}

            // When
            engine.enterStory(story)
        }
    }

    @Test
    fun `Given exit story then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val story = story {}

            // When
            engine.exitStory(story)
        }
    }

    @Test
    fun `Given enter chapter then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val chapter = chapter {}

            // When
            engine.enterChapter(chapter, ChapterTransitions.instant)
        }
    }

    @Test
    fun `Given exit chapter then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val chapter = chapter {}

            // When
            engine.exitChapter(chapter)
        }
    }

    @Test
    fun `Given enter scene then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val scene = scene {}

            // When
            engine.enterScene(scene, Instant())
        }
    }

    @Test
    fun `Given exit scene then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val scene = scene {}

            // When
            engine.exitScene(scene, Instant())
        }
    }

    @Test
    fun `Given enter step when can skip then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val step = then { }

            // When
            engine.enterStep(step, Flags(), true, CancellationToken())
        }
    }

    @Test
    fun `Given enter step when can't skip then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val step = then { }

            // When
            engine.enterStep(step, Flags(), false, CancellationToken())
        }
    }

    @Test
    fun `Given exit step then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val step = then { }

            // When
            engine.exitStep(step, Flags())
        }
    }

    @Test
    fun `Given end processing input then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine

            // When
            engine.endProcessingInput()
        }
    }

    @Test
    fun `Given begin processing input then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine(adapter = TestConsoleAdapter())
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine

            // When
            thread {
                Thread.sleep(100)
                engine.endProcessingInput()
            }
            engine.beginProcessingInput()
        }
    }
}
