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
import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.michel
import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.morgana
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import com.github.benpollarduk.ktvn.text.log.LogElement

/**
 * Provides an [CharacterConfiguration] for an ANSI console. Optionally a map of [characterColors] can be provided where
 * the key is a [Character] and the value is an ANSI colour code.
 */
internal class AnsiConsoleCharacterConfiguration(
    private val consoleController: AnsiConsoleController
) : CharacterConfiguration {
    /**
     * Get an ANSI color for a character.
     */
    private fun getCharacterColor(character: Character) : Int {
        return when (character) {
            morgana -> 91   // red
            michel -> 94    // blue
            else -> 97      // white
        }
    }

    override val emoteAcknowledgementListener: AcknowledgeListener = PassThroughAcknowledgeListener
    override val animateAcknowledgementListener: AcknowledgeListener = PassThroughAcknowledgeListener

    override val speakAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            consoleController.waitForEnter()
            consoleController.clear()
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            // add an element in the log
            consoleController.log.add(LogElement.CharacterLog(character, question.line))

            var questionString = "${character.name}: ${question.line}\n"

            for (i in question.answers.indices) {
                questionString += "  ${i + 1}: ${question.answers[i].line}\n"
            }

            consoleController.print(questionString, getCharacterColor(character))
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
            // add an element in the log
            consoleController.log.add(LogElement.CharacterLog(character, line))

            consoleController.print("${character.name}: $line", getCharacterColor(character))
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
