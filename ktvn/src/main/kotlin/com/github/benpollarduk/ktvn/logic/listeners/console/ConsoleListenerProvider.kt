package com.github.benpollarduk.ktvn.logic.listeners.console

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.listeners.AcknowledgeListener
import com.github.benpollarduk.ktvn.logic.listeners.AnswerListener
import com.github.benpollarduk.ktvn.logic.listeners.AskListener
import com.github.benpollarduk.ktvn.logic.listeners.EmoteListener
import com.github.benpollarduk.ktvn.logic.listeners.ListenerProvider
import com.github.benpollarduk.ktvn.logic.listeners.MoveListener
import com.github.benpollarduk.ktvn.logic.listeners.NarrateListener
import com.github.benpollarduk.ktvn.logic.listeners.SpeakListener

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

    override val emotesAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // continue without acknowledgement
        }
    }

    override val movesAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // continue without acknowledgement
        }
    }

    override val speaksAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            readln()
            clear()
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

            clear()
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

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(line: String, acknowledgement: AcknowledgeListener) {
            println(line)
            acknowledgement.waitFor()
        }
    }

    override val moveListener: MoveListener = object : MoveListener {
        override fun move(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            acknowledgement: AcknowledgeListener
        ) {
            println("${character.name} moveListener from '$fromPosition' to '$toPosition'.")
            acknowledgement.waitFor()
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
            println(question.line)

            for (i in question.answers.indices) {
                println("  ${i + 1}: ${question.answers[i].line}")
            }

            return answerListener.waitFor(question)
        }
    }
}
