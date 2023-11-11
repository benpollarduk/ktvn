package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.layout.Positions.left
import com.github.benpollarduk.ktvn.layout.Positions.right
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.configuration.LayoutConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LayoutTest {
    private val emptySpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val emptyEmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val emptyAnimateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    private val emptyAskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }
    }

    private val acknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // nothing
        }
    }

    private val emptyAnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return question.answers.first()
        }
    }

    private val characterConfiguration: CharacterConfiguration = object : CharacterConfiguration {
        override val emoteAcknowledgementListener: AcknowledgeListener = acknowledgementListener
        override val speakAcknowledgementListener: AcknowledgeListener = acknowledgementListener
        override val animateAcknowledgementListener: AcknowledgeListener = acknowledgementListener
        override val answerListener: AnswerListener = emptyAnswerListener
        override val askListener: AskListener = emptyAskListener
        override val emoteListener: EmoteListener = emptyEmoteListener
        override val animateListener: AnimateListener = emptyAnimateListener
        override val speakListener: SpeakListener = emptySpeakListener
    }

    @Test
    fun `given layout when add character then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterConfiguration
        )

        // When
        layout.add(character, left)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add left of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterConfiguration
        )

        // When
        layout.addLeftOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add right of then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterConfiguration
        )

        // When
        layout.addRightOf(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add above then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterConfiguration
        )

        // When
        layout.addAbove(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when add below then one character`() {
        // Given
        val layout = Layout.createLayout { }
        val character = Character(
            "",
            characterConfiguration
        )

        // When
        layout.addBelow(character)

        // Then
        Assertions.assertEquals(1, layout.characters)
    }

    @Test
    fun `given layout when move character then moves is called`() {
        // Given
        var called = false
        val moveListener = object : MoveListener {
            override fun move(
                character: Character,
                fromPosition: Position,
                toPosition: Position,
                acknowledgement: AcknowledgeListener
            ) {
                called = true
            }
        }
        val configuration: LayoutConfiguration = object : LayoutConfiguration {
            override val moveAcknowledgementListener: AcknowledgeListener = acknowledgementListener
            override val moveListener: MoveListener = moveListener
        }
        val layout = Layout.createLayout { it configure configuration }
        val character = Character(
            "",
            characterConfiguration
        )
        layout.add(character, left)

        // When
        layout.move(character, right)

        // Then
        Assertions.assertTrue(called)
    }
}
