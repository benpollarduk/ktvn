package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.CharacterAskListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.characters.ThinkListener
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.adapters.CharacterAdapter

internal class TestCharacterAdapter : CharacterAdapter {
    override val askListener: CharacterAskListener = object : CharacterAskListener {
        override fun ask(character: Character, question: Question): Answer {
            return question.answers.first()
        }
    }

    override val speakListener: SpeakListener = object : SpeakListener {
        override fun speak(character: Character, line: String) {
            // nothing
        }
    }

    override val thinkListener: ThinkListener = object : ThinkListener {
        override fun think(character: Character, line: String) {
            // nothing
        }
    }

    override val emoteListener: EmoteListener = object : EmoteListener {
        override fun emote(character: Character, emotion: Emotion) {
            // nothing
        }
    }

    override val animateListener: AnimateListener = object : AnimateListener {
        override fun animate(character: Character, animation: Animation) {
            // nothing
        }
    }
}
