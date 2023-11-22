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
@Suppress("TooManyFunctions")
public class DebugGameEngine(
    private val eventTerminal: EventTerminal
) : GameEngine {
    override val log: Log = Log()
    override val progressionController: ProgressionController = ProgressionController()

    override fun playSoundEffect(soundEffect: SoundEffect) {
        // nothing
    }

    override fun acknowledgeCharacterEmotionChanged() {
        // nothing
    }

    override fun acknowledgeCharacterAnimationChanged() {
        // nothing
    }

    override fun acknowledgeCharacterSpeak() {
        // nothing
    }

    override fun acknowledgeNarratorNarrate() {
        // nothing
    }

    override fun acknowledgeLayoutMovement() {
        // nothing
    }

    override fun characterAsksQuestion(character: Character, question: Question) {
        // nothing
    }

    override fun narratorAsksQuestion(narrator: Narrator, question: Question) {
        // nothing
    }

    override fun answerQuestion(question: Question): Answer {
        return question.answers.first()
    }

    override fun characterSpeaks(character: Character, line: String) {
        // nothing
    }

    override fun characterShowsEmotion(character: Character, emotion: Emotion) {
        // nothing
    }

    override fun characterAnimation(character: Character, animation: Animation) {
        // nothing
    }

    override fun characterMoves(character: Character, from: Position, to: Position) {
        // nothing
    }

    override fun narratorNarrates(narrator: Narrator, line: String) {
        // nothing
    }

    override fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        eventTerminal.println(Severity.Info, "Started chapter '${chapter.name}' with transition $transition.")
    }

    override fun exitChapter(chapter: Chapter) {
        eventTerminal.println(Severity.Info, "Finished chapter '${chapter.name}'.")
    }

    override fun enterScene(scene: Scene, transition: SceneTransition) {
        eventTerminal.println(Severity.Info, "Started scene '${scene.name}' with transition $transition.")
    }

    override fun exitScene(scene: Scene, transition: SceneTransition) {
        eventTerminal.println(Severity.Info, "Finished scene '${scene.name}' with transition $transition.")
    }

    override fun clearScene(scene: Scene) {
        // nothing
    }

    override fun enterStep(step: Step, canSkip: Boolean, cancellationToken: CancellationToken) {
        eventTerminal.println(Severity.Info, "Started step '${step.name}' (${step.identifier}).")
    }

    override fun exitStep(step: Step) {
        eventTerminal.println(Severity.Info, "Finished step '${step.name}' (${step.identifier}).")
    }
}
