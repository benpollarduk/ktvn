package com.github.benpollarduk.ktvn.console.configuration

import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an [NarratorConfiguration] for an ANSI console.
 */
internal class AnsiConsoleNarratorConfiguration(
    private val consoleController: AnsiConsoleController
) : NarratorConfiguration {
    override val narrateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            consoleController.waitForEnter()
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            throw NotImplementedError()
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            var questionString = "${question.line}\n"

            for (i in question.answers.indices) {
                questionString += "  ${i + 1}: ${question.answers[i].line}\n"
            }

            consoleController.print(questionString)
            return answerListener.waitFor(question)
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(line: String, acknowledgement: AcknowledgeListener) {
            consoleController.print(line)
            acknowledgement.waitFor()
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
}
