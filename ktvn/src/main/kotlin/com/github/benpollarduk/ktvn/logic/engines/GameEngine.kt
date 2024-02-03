package com.github.benpollarduk.ktvn.logic.engines

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
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.ChapterTransition
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.Story
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import com.github.benpollarduk.ktvn.text.log.Log

/**
 * Defines the functionality that an engine must provide to run a [Game].
 */
@Suppress("TooManyFunctions")
public interface GameEngine {
    /**
     * Get the log. All events can be logged here so that they can be recalled later if needed.
     */
    public val log: Log

    /**
     * Get the progression controller.
     */
    public val progressionController: ProgressionController

    /**
     * Get the volume manager.
     */
    public val volumeManager: VolumeManager

    /**
     * Play a [soundEffect].
     */
    public fun playSoundEffect(soundEffect: SoundEffect)

    /**
     * Handle a [character] asking a [question].
     */
    public fun characterAsksQuestion(character: Character, question: Question)

    /**
     * Handle a [narrator] asking a [question].
     */
    public fun narratorAsksQuestion(narrator: Narrator, question: Question)

    /**
     * Get an [Answer] to a [question].
     */
    public fun getAnswerQuestion(question: Question): Answer

    /**
     * Handle a [character] speaking a [line].
     */
    public fun characterSpeaks(character: Character, line: String)

    /**
     * Handle a [character] thinking a [line].
     */
    public fun characterThinks(character: Character, line: String)

    /**
     * Show a [character] [emotion].
     */
    public fun characterShowsEmotion(character: Character, emotion: Emotion)

    /**
     * Play a [character] [animation].
     */
    public fun characterAnimation(character: Character, animation: Animation)

    /**
     * Move a [character] [from] a position [to] another position with a specified [transition].
     */
    public fun characterMoves(character: Character, from: Position, to: Position, transition: LayoutTransition)

    /**
     * Handle a [narrator] narrating a [line].
     */
    public fun narratorNarrates(narrator: Narrator, line: String)

    /**
     * Enter a [story].
     */
    public fun enterStory(story: Story)

    /**
     * Exit a [story] .
     */
    public fun exitStory(story: Story)

    /**
     * Enter a [chapter] with a [transition].
     */
    public fun enterChapter(chapter: Chapter, transition: ChapterTransition)

    /**
     * Exit a [chapter] .
     */
    public fun exitChapter(chapter: Chapter)

    /**
     * Enter a [scene] with a [transition].
     */
    public fun enterScene(scene: Scene, transition: SceneTransition)

    /**
     * Exit a [scene] with a [transition].
     */
    public fun exitScene(scene: Scene, transition: SceneTransition)

    /**
     * Clear a [scene].
     */
    public fun clearScene(scene: Scene)

    /**
     * Enter a [step] with [flags]. When the step can be skipped [canSkip] will be true. A [cancellationToken] must be
     * provided to support cancellation.
     */
    public fun enterStep(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken)

    /**
     * Exit a [step].
     */
    public fun exitStep(step: Step, flags: Flags)
}
