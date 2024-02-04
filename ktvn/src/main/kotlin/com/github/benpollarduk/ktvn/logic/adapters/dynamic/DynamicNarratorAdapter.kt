package com.github.benpollarduk.ktvn.logic.adapters.dynamic

import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.NarratorAskListener
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.NarratorAdapter
import com.github.benpollarduk.ktvn.logic.engines.GameEngine
import com.github.benpollarduk.ktvn.text.log.LogElement

/**
 * Provides a [NarratorAdapter] with a [gameEngine] that can be specified after initialization.
 */
internal class DynamicNarratorAdapter(internal var gameEngine: GameEngine? = null) : NarratorAdapter {
    override val askListener: NarratorAskListener = object : NarratorAskListener {
        override fun ask(narrator: Narrator, question: Question): Answer {
            gameEngine?.log?.add(LogElement.NarratorLog(narrator, question.line))
            gameEngine?.narratorAsksQuestion(narrator, question)
            return gameEngine?.getAnswerQuestion(question) ?: answer { }
        }
    }

    override val narrateListener: NarrateListener = object : NarrateListener {
        override fun narrate(narrator: Narrator, line: String) {
            gameEngine?.log?.add(LogElement.NarratorLog(narrator, line))
            gameEngine?.narratorNarrates(narrator, line)
        }
    }
}
