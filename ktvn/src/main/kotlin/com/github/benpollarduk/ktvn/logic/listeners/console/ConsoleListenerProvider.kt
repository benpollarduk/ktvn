package com.github.benpollarduk.ktvn.logic.listeners.console

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

/**
 * Provides a default listener provider for a Console.
 */
public object ConsoleListenerProvider : ListenerProvider {
    /**
     * Clear the console. This only works on terminals that support ANSI.
     */
    public fun clear() {
        print("\u001b[H\u001b[2J")
    }

    override val emotesAcknowledgement: Acknowledges = object : Acknowledges {
        override fun waitFor() {
            // continue without acknowledgement
        }
    }

    override val movesAcknowledgement: Acknowledges = object : Acknowledges {
        override fun waitFor() {
            // continue without acknowledgement
        }
    }

    override val speaksAcknowledgement: Acknowledges = object : Acknowledges {
        override fun waitFor() {
            readln()
            clear()
        }
    }

    override val answers: Answers = object : Answers {
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

    override val speaks: Speaks = object : Speaks {
        override fun invoke(character: Character, line: String, acknowledgement: Acknowledges) {
            println("${character.name}: $line")
            acknowledgement.waitFor()
        }
    }

    override val emotes: Emotes = object : Emotes {
        override fun invoke(character: Character, emotion: Emotion, acknowledgement: Acknowledges) {
            println("${character.name} looks $emotion.")
            acknowledgement.waitFor()
        }
    }

    override val narrates: Narrates = object : Narrates {
        override fun invoke(line: String, acknowledgement: Acknowledges) {
            println(line)
            acknowledgement.waitFor()
        }
    }

    override val moves: Moves = object : Moves {
        override fun invoke(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            acknowledgement: Acknowledges
        ) {
            println("${character.name} moves from '$fromPosition' to '$toPosition'.")
            acknowledgement.waitFor()
        }
    }

    override val asks: Asks = object : Asks {
        override fun invoke(character: Character, question: Question, answers: Answers): Answer {
            println("${character.name}: ${question.line}")

            for (i in question.answers.indices) {
                println("  ${i + 1}: ${question.answers[i].line}")
            }

            return answers.waitFor(question)
        }

        override fun invoke(narrator: Narrator, question: Question, answers: Answers): Answer {
            println(question.line)

            for (i in question.answers.indices) {
                println("  ${i + 1}: ${question.answers[i].line}")
            }

            return answers.waitFor(question)
        }
    }
}
