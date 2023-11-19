package com.github.benpollarduk.ktvn.logic.configuration.default

import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a default [NarratorConfiguration] with a specified [gameController].
 */
internal class DefaultNarratorConfiguration(private val gameController: GameController) : NarratorConfiguration {
    override val narrateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameController.acknowledgeNarratorNarrate()
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            throw NotImplementedError()
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            gameController.narratorAskedQuestion(narrator, question)
            return answerListener.waitFor(question)
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(narrator: Narrator, line: String, acknowledgement: AcknowledgeListener) {
            gameController.narratorNarrates(narrator, line)
            acknowledgement.waitFor()
        }
    }

    override val answerListener: AnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return gameController.getAnswerToQuestion(question)
        }
    }
}
