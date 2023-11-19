package com.github.benpollarduk.ktvn.logic.configuration.default

import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.NarratorAskListener
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import com.github.benpollarduk.ktvn.text.log.LogElement

/**
 * Provides a default [NarratorConfiguration] with a specified [gameController].
 */
internal class DefaultNarratorConfiguration(private val gameController: GameController) : NarratorConfiguration {
    override val narrateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameController.acknowledgeNarratorNarrate()
        }
    }

    override val askListener: NarratorAskListener = object : NarratorAskListener {
        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            gameController.log.add(LogElement.NarratorLog(narrator, question.line))
            gameController.narratorAskedQuestion(narrator, question)
            return answerListener.waitFor(question)
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(narrator: Narrator, line: String, acknowledgement: AcknowledgeListener) {
            gameController.log.add(LogElement.NarratorLog(narrator, line))
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
