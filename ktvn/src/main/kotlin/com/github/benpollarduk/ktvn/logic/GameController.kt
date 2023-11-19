package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.audio.SoundEffect
import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.text.log.Log
import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantLock

/**
 * A class that functions as a controller for a [Game].
 */
@Suppress("TooManyFunctions")
public open class GameController {
    /**
     * A [CountDownLatch] that can be used to signal that an acknowledgement was received.
     */
    protected var acknowledgementReceived: CountDownLatch? = null

    /**
     * A [ReentrantLock] that can be used to control access to the variables within this class.
     */
    protected val lock: ReentrantLock = ReentrantLock()

    /**
     * Get or set the progression mode.
     */
    public var progressionMode: ProgressionMode = ProgressionMode.WaitForConfirmation

    /**
     * Get the log. All events can be logged here so that they can be recalled later if needed.
     */
    public val log: Log = Log()

    /**
     * Get the auto. This controls all auto-acknowledgements.
     */
    public val auto: Auto = Auto()

    /**
     * Await an acknowledgment. If [canSkipCurrentStep] is set true the current step is regarded as one that can be
     * skipped. A [cancellationToken] must be provided to support cancellation.
     */
    public fun awaitAcknowledgement(canSkipCurrentStep: Boolean, cancellationToken: CancellationToken) {
        try {
            lock.lock()
            acknowledgementReceived = CountDownLatch(1)
        } finally {
            lock.unlock()
        }

        // some progression modes control flow differently
        when (val mode = progressionMode) {
            is ProgressionMode.Auto -> {
                if (auto.wait(mode.postDelayInMs, cancellationToken)) {
                    acknowledgementReceived?.countDown()
                }
            }
            is ProgressionMode.Skip -> {
                if (canSkipCurrentStep || mode.skipUnseen) {
                    acknowledgementReceived?.countDown()
                }
            }
            is ProgressionMode.WaitForConfirmation -> {
                // no handling
            }
        }

        // wait for the acknowledgement to be received
        acknowledgementReceived?.await()

        try {
            lock.lock()
            acknowledgementReceived = null
        } finally {
            lock.unlock()
        }
    }

    /**
     * Play a [soundEffect].
     */
    public open fun playSoundEffect(soundEffect: SoundEffect) {
        throw NotImplementedError()
    }

    /**
     * Acknowledge a [Character] [Emotion] changed.
     */
    public open fun acknowledgeCharacterEmotionChanged() {
        throw NotImplementedError()
    }

    /**
     * Acknowledge a [Character] [Animation] changed.
     */
    public open fun acknowledgeCharacterAnimationChanged() {
        throw NotImplementedError()
    }

    /**
     * Acknowledge a [Character] line of speech.
     */
    public open fun acknowledgeCharacterSpeak() {
        throw NotImplementedError()
    }

    /**
     * Acknowledge a [Narrator] narration.
     */
    public open fun acknowledgeNarratorNarrate() {
        throw NotImplementedError()
    }

    /**
     * Acknowledge a movement in a [Layout].
     */
    public open fun acknowledgeLayoutMovement() {
        throw NotImplementedError()
    }

    /**
     * Handle a [character] asking a [question].
     */
    public open fun characterAskedQuestion(character: Character, question: Question) {
        throw NotImplementedError()
    }

    /**
     * Handle a [narrator] asking a [question].
     */
    public open fun narratorAskedQuestion(narrator: Narrator, question: Question) {
        throw NotImplementedError()
    }

    /**
     * Get an [Answer] to a [question].
     */
    public open fun getAnswerToQuestion(question: Question): Answer {
        throw NotImplementedError()
    }

    /**
     * Handle a [character] speaking a [line].
     */
    public open fun characterSpeaks(character: Character, line: String) {
        throw NotImplementedError()
    }

    /**
     * Show a [character] [emotion].
     */
    public open fun characterEmotion(character: Character, emotion: Emotion) {
        throw NotImplementedError()
    }

    /**
     * Play a [character] [animation].
     */
    public open fun characterAnimation(character: Character, animation: Animation) {
        throw NotImplementedError()
    }

    /**
     * Move a [character] [from] a position [to] another position.
     */
    public open fun moveCharacter(character: Character, from: Position, to: Position) {
        throw NotImplementedError()
    }

    /**
     * Handle a [narrator] narrating a [line].
     */
    public open fun narratorNarrates(narrator: Narrator, line: String) {
        throw NotImplementedError()
    }

    /**
     * Enter a [chapter] with a [transition].
     */
    public open fun enterChapter(chapter: Chapter, transition: ChapterTransition) {
        throw NotImplementedError()
    }

    /**
     * Exit a [chapter] .
     */
    public open fun exitChapter(chapter: Chapter) {
        throw NotImplementedError()
    }

    /**
     * Enter a [scene] with a [transition].
     */
    public open fun enterScene(scene: Scene, transition: SceneTransition) {
        throw NotImplementedError()
    }

    /**
     * Exit a [scene] with a [transition].
     */
    public open fun exitScene(scene: Scene, transition: SceneTransition) {
        throw NotImplementedError()
    }

    /**
     * Clear a [scene].
     */
    public open fun clearScene(scene: Scene) {
        throw NotImplementedError()
    }

    /**
     * Enter a [step]. When the step can be skipped [canSkip] will be true. A [cancellationToken] must be provided to
     * support cancellation.
     */
    public open fun enterStep(step: Step, canSkip: Boolean, cancellationToken: CancellationToken) {
        throw NotImplementedError()
    }

    /**
     * Exit a [step].
     */
    public open fun exitStep(step: Step) {
        throw NotImplementedError()
    }
}
