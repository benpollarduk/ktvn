package com.github.benpollarduk.ktvn.logic.adapters.static

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.CharacterAskListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import com.github.benpollarduk.ktvn.text.log.LogElement

/**
 * Provides a [CharacterAdapter] with a specified [gameEngine].
 */
internal class StaticCharacterAdapter(private val gameEngine: GameEngine) : CharacterAdapter {
    override val emoteAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameEngine.acknowledgeCharacterEmotionChanged()
        }
    }

    override val animateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameEngine.acknowledgeCharacterAnimationChanged()
        }
    }

    override val speakAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameEngine.acknowledgeCharacterSpeak()
        }
    }

    override val askListener: CharacterAskListener = object : CharacterAskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            gameEngine.log.add(LogElement.CharacterLog(character, question.line))
            gameEngine.characterAsksQuestion(character, question)
            return answerListener.waitFor(question)
        }
    }

    override val answerListener: AnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return gameEngine.answerQuestion(question)
        }
    }

    override val speakListener: SpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            gameEngine.log.add(LogElement.CharacterLog(character, line))
            gameEngine.characterSpeaks(character, line)
            acknowledgement.waitFor()
        }
    }

    override val emoteListener: EmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
            gameEngine.characterShowsEmotion(character, emotion)
            acknowledgement.waitFor()
        }
    }

    override val animateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation, acknowledgement: AcknowledgeListener) {
            gameEngine.characterAnimation(character, animation)
            acknowledgement.waitFor()
        }
    }
}
