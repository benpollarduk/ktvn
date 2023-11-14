package com.github.benpollarduk.ktvn.configuration.test

import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.NarratorConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

internal class TestNarratorConfiguration : NarratorConfiguration {
    override val narrateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // nothing
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            return question.answers.first()
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(line: String, acknowledgement: AcknowledgeListener) {
            // nothing
        }
    }

    override val answerListener: AnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return question.answers.first()
        }
    }
}
