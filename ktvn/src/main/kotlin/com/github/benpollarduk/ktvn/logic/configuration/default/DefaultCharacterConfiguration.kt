package com.github.benpollarduk.ktvn.logic.configuration.default

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.configuration.CharacterConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a default [CharacterConfiguration] with a specified [gameController].
 */
internal class DefaultCharacterConfiguration(private val gameController: GameController) : CharacterConfiguration {
    override val emoteAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameController.acknowledgeCharacterEmotionChanged()
        }
    }

    override val animateAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameController.acknowledgeCharacterAnimationChanged()
        }
    }

    override val speakAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameController.acknowledgeCharacterSpeak()
        }
    }

    override val askListener: AskListener = object : AskListener {
        override fun ask(character: Character, question: Question, answerListener: AnswerListener): Answer {
            gameController.characterAskedQuestion(character, question)
            return answerListener.waitFor(question)
        }

        override fun ask(narrator: Narrator, question: Question, answerListener: AnswerListener): Answer {
            throw NotImplementedError()
        }
    }

    override val answerListener: AnswerListener = object : AnswerListener {
        override fun waitFor(question: Question): Answer {
            return gameController.getAnswerToQuestion(question)
        }
    }

    override val speakListener: SpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener) {
            gameController.characterSpeaks(character, line)
            acknowledgement.waitFor()
        }
    }

    override val emoteListener: EmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener) {
            gameController.characterEmotion(character, emotion)
            acknowledgement.waitFor()
        }
    }

    override val animateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation, acknowledgement: AcknowledgeListener) {
            gameController.characterAnimation(character, animation)
            acknowledgement.waitFor()
        }
    }
}
