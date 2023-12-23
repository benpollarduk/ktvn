package com.github.benpollarduk.ktvn.logic.adapters.dynamic

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.CharacterAskListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.characters.ThinkListener
import com.github.benpollarduk.ktvn.characters.animations.AnimateListener
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.text.log.LogElement

/**
 * Provides a [CharacterAdapter] with a [gameEngine] that can be specified after initialization.
 */
internal class DynamicCharacterAdapter(internal var gameEngine: GameEngine? = null) : CharacterAdapter {
    override val askListener: CharacterAskListener = object : CharacterAskListener {
        override fun ask(character: Character, question: Question): Answer {
            gameEngine?.log?.add(LogElement.CharacterLog(character, question.line))
            gameEngine?.characterAsksQuestion(character, question)
            return gameEngine?.getAnswerQuestion(question) ?: answer { }
        }
    }

    override val speakListener: SpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String) {
            gameEngine?.log?.add(LogElement.CharacterLog(character, line))
            gameEngine?.characterSpeaks(character, line)
        }
    }
    override val thinkListener: ThinkListener = object : ThinkListener {
        override fun think(character: Character, line: String) {
            gameEngine?.log?.add(LogElement.CharacterLog(character, line))
            gameEngine?.characterThinks(character, line)
        }
    }

    override val emoteListener: EmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion) {
            gameEngine?.characterShowsEmotion(character, emotion)
        }
    }

    override val animateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation) {
            gameEngine?.characterAnimation(character, animation)
        }
    }
}
