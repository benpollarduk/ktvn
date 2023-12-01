package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.StoryBeginParameters
import java.util.concurrent.locks.ReentrantLock

/**
 * An executable game with a specified [visualNovel] and optional [gameSave] and [restorePoint].
 */
public class Game(
    private val visualNovel: VisualNovel,
    private val gameSave: GameSave = GameSave.EMPTY,
    private val restorePoint: RestorePoint = RestorePoint.EMPTY
) {
    private val cancellationToken = CancellationToken()
    private val endingsReached: MutableList<Ending> = mutableListOf()
    private var startTimeInSeconds: Long = 0
    private var endTimeInSeconds: Long = 0
    private val lock = ReentrantLock()

    /**
     * Get if this game is executing
     */
    public var isExecuting: Boolean = false
        private set

    /**
     * Begin execution of the game. Returns a [GameExecutionResult].
     */
    internal fun execute(): GameExecutionResult {
        val configuration = visualNovel.configuration
        val story = visualNovel.story
        isExecuting = true
        startTimeInSeconds = System.currentTimeMillis() / MILLISECONDS_PER_SECOND

        val ending = story.begin(
            StoryBeginParameters(
                Flags.fromMap(restorePoint.flags),
                restorePoint.storyRestorePoint,
                configuration.gameAdapter.storyAdapter,
                configuration.stepTracker,
                cancellationToken
            )
        )

        try {
            lock.lock()

            if (ending != Ending.none && !endingsReached.contains(ending)) {
                endingsReached.add(ending)
            }
        } finally {
            lock.unlock()
        }

        endTimeInSeconds = System.currentTimeMillis() / MILLISECONDS_PER_SECOND
        isExecuting = false

        return if (cancellationToken.wasCancelled) {
            GameExecutionResult.cancelled
        } else {
            GameExecutionResult(true, ending, getGameSave())
        }
    }

    /**
     * End execution of the game.
     */
    internal fun end() {
        cancellationToken.cancel()
    }

    /**
     * Get a [RestorePoint] for the game with a specified [name].
     */
    public fun getRestorePoint(name: String): RestorePoint {
        val s = visualNovel.story

        return RestorePoint(
            name,
            s.flags.toMap(),
            s.createRestorePoint()
        )
    }

    /**
     * Get a [GameSave] for the game.
     */
    public fun getGameSave(): GameSave {
        val additionalTimeInSeconds = if (isExecuting) {
            (System.currentTimeMillis() / MILLISECONDS_PER_SECOND) - startTimeInSeconds
        } else {
            endTimeInSeconds - startTimeInSeconds
        }

        val endings: List<Ending>

        try {
            lock.lock()
            endings = endingsReached.toList()
        } finally {
            lock.unlock()
        }

        return GameSave(
            gameSave.totalSeconds + additionalTimeInSeconds,
            endings
        )
    }

    private companion object {
        private const val MILLISECONDS_PER_SECOND: Int = 1000
    }
}
