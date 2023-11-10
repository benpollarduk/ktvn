package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.audio.AudioType
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.logic.configuration.InteractionConfiguration
import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.SpeakListener

/**
 * Provides a default configuration for an ANSI compatible Console.
 */
public object AnsiConsoleInteractionConfiguration : InteractionConfiguration {
    /**
     * Clear the console.
     */
    public fun clearConsole() {
        print("\u001b[H\u001b[2J")
    }

    private val passThroughAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // continue without acknowledgement
        }
    }

    override val emotesAcknowledgementListener: AcknowledgeListener = passThroughAcknowledgementListener

    override val movesAcknowledgementListener: AcknowledgeListener = passThroughAcknowledgementListener

    override val animateAcknowledgementListener: AcknowledgeListener = passThroughAcknowledgementListener

    override val speaksAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            readln()
            clearConsole()
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

            clearConsole()
            return question.answers[index]
        }
    }

    override val speakListener: SpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            println("${character.name}: $line")
            acknowledgement.waitFor()
        }
    }

    override val audioListener: AudioListener = object : AudioListener {
        override fun play(key: String) {
            println("BGM: $key")
        }

        override fun sfx(key: String) {
            println("SFX: $key")
        }

        override fun stop(type: AudioType) {
            println("Stop: $type")
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
