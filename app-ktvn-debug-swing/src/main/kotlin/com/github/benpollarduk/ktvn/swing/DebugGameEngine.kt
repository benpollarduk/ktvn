package com.github.benpollarduk.ktvn.swing

import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.ProgressionController
import com.github.benpollarduk.ktvn.logic.Question
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.swing.components.EventTerminal
import com.github.benpollarduk.ktvn.text.log.Log

/**
 * A class that functions as an engine for the debugger.
 */
public class DebugGameEngine(
    private val eventTerminal: EventTerminal
) : GameEngine {
    override val log: Log = Log()
    override val progressionController: ProgressionController = ProgressionController()

    override fun playSoundEffect(soundEffect: SoundEffect) {

    }

    override fun acknowledgeCharacterEmotionChanged() {

    }

    override fun acknowledgeCharacterAnimationChanged() {

    }

    override fun acknowledgeCharacterSpeak() {

    }

    override fun acknowledgeNarratorNarrate() {

    }

    override fun acknowledgeLayoutMovement() {

    }

    override fun characterAsksQuestion(character: Character, question: Question) {

    }

    override fun narratorAsksQuestion(narrator: Narrator, question: Question) {

    }

    override fun answerQuestion(question: Question): Answer {
        return question.answers.first()
    }

    override fun characterSpeaks(character: Character, line: String) {

    }

    override fun characterShowsEmotion(character: Character, emotion: Emotion) {

    }

    override fun characterAnimation(character: Character, animation: Animation) {

    }

    override fun characterMoves(character: Character, from: Position, to: Position) {

    }

    override fun narratorNarrates(narrator: Narrator, line: String) {

    }

    override fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        eventTerminal.println(Severity.Info,"Started chapter '${chapter.name}' with transition $transition.")
    }

    override fun exitChapter(chapter: Chapter) {
        eventTerminal.println(Severity.Info,"Finished chapter '${chapter.name}'.")
    }

    override fun enterScene(scene: Scene, transition: SceneTransition) {
        eventTerminal.println(Severity.Info,"Started scene '${scene.name}' with transition $transition.")
    }

    override fun exitScene(scene: Scene, transition: SceneTransition) {
        eventTerminal.println(Severity.Info,"Finished scene '${scene.name}' with transition $transition.")
    }

    override fun clearScene(scene: Scene) {
        TODO("Not yet implemented")
    }

    override fun enterStep(step: Step, canSkip: Boolean, cancellationToken: CancellationToken) {
        eventTerminal.println(Severity.Info,"Started step '${step.name}' (${step.identifier}).")
    }

    override fun exitStep(step: Step) {
        eventTerminal.println(Severity.Info,"Finished step '${step.name}' (${step.identifier}).")
    }
}
