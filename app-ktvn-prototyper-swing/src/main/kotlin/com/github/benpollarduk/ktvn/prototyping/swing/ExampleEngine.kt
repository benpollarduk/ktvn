package com.github.benpollarduk.ktvn.prototyping.swing

import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.audio.VolumeManager
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.ProgressionController
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.engines.GameEngine
import com.github.benpollarduk.ktvn.structure.*
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import com.github.benpollarduk.ktvn.text.log.Log

class ExampleEngine : GameEngine {
    override val log: Log = Log()
    override val progressionController: ProgressionController = ProgressionController()
    override val volumeManager: VolumeManager = VolumeManager()

    override fun playSoundEffect(soundEffect: SoundEffect) {
        TODO("Not yet implemented")
    }

    override fun characterAsksQuestion(character: Character, question: Question) {
        TODO("Not yet implemented")
    }

    override fun narratorAsksQuestion(narrator: Narrator, question: Question) {
        TODO("Not yet implemented")
    }

    override fun getAnswerToQuestion(question: Question): Answer {
        TODO("Not yet implemented")
    }

    override fun characterSpeaks(character: Character, line: String) {
        TODO("Not yet implemented")
    }

    override fun characterThinks(character: Character, line: String) {
        TODO("Not yet implemented")
    }

    override fun characterShowsEmotion(character: Character, emotion: Emotion) {
        TODO("Not yet implemented")
    }

    override fun characterAnimation(character: Character, animation: Animation) {
        TODO("Not yet implemented")
    }

    override fun characterMoves(character: Character, from: Position, to: Position, transition: LayoutTransition) {
        TODO("Not yet implemented")
    }

    override fun narratorNarrates(narrator: Narrator, line: String) {
        TODO("Not yet implemented")
    }

    override fun enterStory(story: Story) {
        TODO("Not yet implemented")
    }

    override fun exitStory(story: Story) {
        TODO("Not yet implemented")
    }

    override fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        TODO("Not yet implemented")
    }

    override fun exitChapter(chapter: Chapter) {
        TODO("Not yet implemented")
    }

    override fun enterScene(scene: Scene, transition: SceneTransition) {
        TODO("Not yet implemented")
    }

    override fun exitScene(scene: Scene, transition: SceneTransition) {
        TODO("Not yet implemented")
    }

    override fun clearScene(scene: Scene) {
        TODO("Not yet implemented")
    }

    override fun enterStep(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken) {
        TODO("Not yet implemented")
    }

    override fun exitStep(step: Step, flags: Flags) {
        TODO("Not yet implemented")
    }
}