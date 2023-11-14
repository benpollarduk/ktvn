package com.github.benpollarduk.ktvn.console.configuration

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
internal class AnsiConsoleCharacterConfiguration(
    private val consoleController: AnsiConsoleController
) : CharacterConfiguration {
    private val passThroughAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // continue without acknowledgement
        }
    }

    override val emoteAcknowledgementListener: AcknowledgeListener = passThroughAcknowledgementListener

    override val animateAcknowledgementListener: AcknowledgeListener = passThroughAcknowledgementListener

    override val speakAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            consoleController.waitForEnter()
            consoleController.clear()
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            var questionString = "${character.name}: ${question.line}\n"

            for (i in question.answers.indices) {
                questionString += "  ${i + 1}: ${question.answers[i].line}\n"
            }

            consoleController.print(questionString)
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
                    consoleController.waitForInput().toInt() - 1
                } catch (e: NumberFormatException) {
                    Int.MIN_VALUE
                }
            }

            consoleController.clear()
            return question.answers[index]
        }
    }

    override val speakListener: SpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            consoleController.print("${character.name}: $line")
            acknowledgement.waitFor()
        }
    }

    override val emoteListener: EmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
            consoleController.printlnDirectTemp("${character.name} looks $emotion.")
            acknowledgement.waitFor()
        }
    }

    override val animateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation, acknowledgement: AcknowledgeListener) {
            consoleController.printlnDirectTemp("${character.name} begins $animation.")
            acknowledgement.waitFor()
        }
    }
}
