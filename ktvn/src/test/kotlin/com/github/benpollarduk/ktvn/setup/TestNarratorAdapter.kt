package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.NarratorAskListener
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter

internal class TestNarratorAdapter : NarratorAdapter {
    override val askListener: NarratorAskListener = object : NarratorAskListener {
        override fun ask(narrator: Narrator, question: Question): Answer {
            return question.answers.first()
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(narrator: Narrator, line: String) {
            // nothing
        }
    }
}
