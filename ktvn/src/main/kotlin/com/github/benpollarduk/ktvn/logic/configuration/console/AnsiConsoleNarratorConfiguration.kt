package com.github.benpollarduk.ktvn.logic.configuration.console

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
internal class AnsiConsoleNarratorConfiguration : NarratorConfiguration {
    override val narrateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // pass through
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            throw NotImplementedError()
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            println(question.line)

            for (i in question.answers.indices) {
                println("  ${i + 1}: ${question.answers[i].line}")
            }

            return answerListener.waitFor(question)
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(line: String, acknowledgement: AcknowledgeListener) {
            println(line)
            acknowledgement.waitFor()
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
}
