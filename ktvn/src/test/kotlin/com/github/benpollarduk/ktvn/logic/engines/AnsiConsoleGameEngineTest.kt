package com.github.benpollarduk.ktvn.logic.engines

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
    private val inputDelayInMs = 1000L

    @Test
    fun `Given character speaks when monitoring log then log has one entry`() {
        // Given
        val engine = AnsiConsoleGameEngine()
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)
        val preSpeak = engine.log.toArray().size

        // When
        thread {
            Thread.sleep(inputDelayInMs)
            engine.progressionController.triggerAcknowledgementLatch()
        }
        character.says("Test")
        val postSpeak = engine.log.toArray().size

        // Then
        Assertions.assertEquals(0, preSpeak)
        Assertions.assertEquals(1, postSpeak)
    }

    @Test
    fun `Given character thinks when monitoring log then log has one entry`() {
        // Given
        val engine = AnsiConsoleGameEngine()
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)
        val preSpeak = engine.log.toArray().size

        // When
        thread {
            Thread.sleep(inputDelayInMs)
            engine.progressionController.triggerAcknowledgementLatch()
        }
        character.thinks("Test")
        val postSpeak = engine.log.toArray().size

        // Then
        Assertions.assertEquals(0, preSpeak)
        Assertions.assertEquals(1, postSpeak)
    }

    @Test
    fun `Given character asks question when one answer then first returned`() {
        // Given
        val engine = AnsiConsoleGameEngine()
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
        thread {
            Thread.sleep(inputDelayInMs)
            engine.setInput("1")
            engine.progressionController.triggerAcknowledgementLatch()
        }
        val result = character.asks(question)

        // Then
        Assertions.assertEquals(question.answers.first(), result)
    }

    @Test
    fun `Given character emotes then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val character = Character("", configuration.gameAdapter.characterAdapter)

            // When
            character.looks(happy)
        }
    }

    @Test
    fun `Given character animates then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val character = Character("", configuration.gameAdapter.characterAdapter)

            // When
            character.begins(Laugh(10.0, 10, 10))
        }
    }

    @Test
    fun `Given character moves then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine
            val character = Character("", configuration.gameAdapter.characterAdapter)
            val layout = createLayout {
                this configure configuration.gameAdapter.layoutAdapter
            }

            // When
            layout.moveLeft(character)
        }
    }

    @Test
    fun `Given narrator narrates when monitoring log then log has one entry`() {
        // Given
        val engine = AnsiConsoleGameEngine()
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val narrator = Narrator(configuration.gameAdapter.narratorAdapter)
        val preNarrate = engine.log.toArray().size

        // When
        thread {
            Thread.sleep(inputDelayInMs)
            engine.progressionController.triggerAcknowledgementLatch()
        }
        narrator.narrates("Test")
        val postNarrate = engine.log.toArray().size

        // Then
        Assertions.assertEquals(0, preNarrate)
        Assertions.assertEquals(1, postNarrate)
    }

    @Test
    fun `Given narrator asks question when monitoring log then first returned`() {
        // Given
        val engine = AnsiConsoleGameEngine()
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
        thread {
            Thread.sleep(inputDelayInMs)
            engine.setInput("1")
            engine.progressionController.triggerAcknowledgementLatch()
        }
        val result = narrator.asks(question)

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
            val engine = AnsiConsoleGameEngine()
            val configuration = DynamicGameConfiguration()
            configuration.engine = engine

            // When
            thread {
                Thread.sleep(inputDelayInMs)
                engine.progressionController.triggerAcknowledgementLatch()
                engine.endProcessingInput()
            }
            engine.beginProcessingInput()
        }
    }
}
