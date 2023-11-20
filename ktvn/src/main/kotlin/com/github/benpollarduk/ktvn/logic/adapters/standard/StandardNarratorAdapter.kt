package com.github.benpollarduk.ktvn.logic.adapters.standard

import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.NarratorAskListener
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import com.github.benpollarduk.ktvn.text.log.LogElement

/**
 * Provides a standard [NarratorAdapter] with a specified [gameEngine].
 */
internal class StandardNarratorAdapter(private val gameEngine: GameEngine) : NarratorAdapter {
    override val narrateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameEngine.acknowledgeNarratorNarrate()
        }
    }

    override val askListener: NarratorAskListener = object : NarratorAskListener {
        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            gameEngine.log.add(LogElement.NarratorLog(narrator, question.line))
            gameEngine.narratorAsksQuestion(narrator, question)
            return answerListener.waitFor(question)
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(narrator: Narrator, line: String, acknowledgement: AcknowledgeListener) {
            gameEngine.log.add(LogElement.NarratorLog(narrator, line))
            gameEngine.narratorNarrates(narrator, line)
            acknowledgement.waitFor()
        }
    }

    override val answerListener: AnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return gameEngine.answerQuestion(question)
        }
    }
}
