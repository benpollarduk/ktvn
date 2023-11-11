package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an [CharacterConfiguration] for an ANSI console.
 */
internal class AnsiConsoleCharacterConfiguration : CharacterConfiguration {
    private val passThroughAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // continue without acknowledgement
        }
    }

    override val emoteAcknowledgementListener: AcknowledgeListener = passThroughAcknowledgementListener

    override val animateAcknowledgementListener: AcknowledgeListener = passThroughAcknowledgementListener

    override val speakAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            readln()
            AnsiConsoleGameConfiguration.clearConsole()
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            println("${character.name}: ${question.line}")

            for (i in question.answers.indices) {
                println("  ${i + 1}: ${question.answers[i].line}")
            }

            return answerListener.waitFor(question)
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            throw NotImplementedError()
        }
    }

    override val answerListener: AnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            var index = Int.MIN_VALUE

            while (index == Int.MIN_VALUE) {
                index = try {
                    readln().toInt() - 1
                } catch (e: NumberFormatException) {
                    Int.MIN_VALUE
                }
            }

            AnsiConsoleGameConfiguration.clearConsole()
            return question.answers[index]
        }
    }

    override val speakListener: SpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            println("${character.name}: $line")
            acknowledgement.waitFor()
        }
    }

    override val emoteListener: EmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
            println("${character.name} looks $emotion.")
            acknowledgement.waitFor()
        }
    }

    override val animateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation, acknowledgement: AcknowledgeListener) {
            println("${character.name} begins $animation.")
            acknowledgement.waitFor()
        }
    }
}
