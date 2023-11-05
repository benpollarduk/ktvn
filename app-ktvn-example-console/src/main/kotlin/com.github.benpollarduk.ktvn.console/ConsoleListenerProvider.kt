package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.Acknowledges
import com.github.benpollarduk.ktvn.logic.listeners.Answers
import com.github.benpollarduk.ktvn.logic.listeners.Asks
import com.github.benpollarduk.ktvn.logic.listeners.Emotes
import com.github.benpollarduk.ktvn.logic.listeners.ListenerProvider
import com.github.benpollarduk.ktvn.logic.listeners.Moves
import com.github.benpollarduk.ktvn.logic.listeners.Narrates
import com.github.benpollarduk.ktvn.logic.listeners.Speaks

internal object ConsoleListenerProvider : ListenerProvider {
    private fun clear() {
        print("\u001b[H\u001b[2J")
    }

    override val acknowledges = object : Acknowledges {
        override fun waitFor() {
            readln()
            clear()
        }
    }

    override val answers = object : Answers {
        override fun waitFor(question: Question): Answer {
            var index = Int.MIN_VALUE

            while (index == Int.MIN_VALUE) {
                index = try {
                    readln().toInt() - 1
                } catch (e: NumberFormatException) {
                    Int.MIN_VALUE
                }
            }

            clear()
            return question.answers[index]
        }
    }

    override val speaks = object : Speaks {
        override fun invoke(character: Character, line: String, acknowledgement: Acknowledges) {
            println("${character.name}: $line")
            acknowledgement.waitFor()
        }
    }

    override val emotes = object : Emotes {
        override fun invoke(character: Character, emotion: Emotion) {
            println("${character.name} looks $emotion.")
        }
    }

    override val narrates = object : Narrates {
        override fun invoke(line: String, acknowledgement: Acknowledges) {
            println(line)
            acknowledgement.waitFor()
        }
    }

    override val moves = object : Moves {
        override fun invoke(character: Character, fromPosition: Position, toPosition: Position) {
            println("${character.name} moves from $fromPosition to $toPosition.")
        }
    }

    override val asks = object : Asks {
        override fun invoke(character: Character, question: Question, answers: Answers): Answer {
            println("${character.name}: ${question.line}")
            for (i in question.answers.indices) {
                println("  ${i + 1}: ${question.answers[i].line}")
            }
            return answers.waitFor(question)
        }

        override fun invoke(narrator: Narrator, question: Question, answers: Answers): Answer {
            println(question.line)
            return answers.waitFor(question)
        }
    }
}
