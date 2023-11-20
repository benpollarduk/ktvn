package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.logic.structure.StoryBeginParameters
import java.util.concurrent.locks.ReentrantLock

/**
 * An executable game with a specified [story], [gameConfiguration] and optional [gameSave] and [restorePoint].
 */
public class Game(
    private val story: Story,
    private val gameConfiguration: GameConfiguration,
    private val gameSave: GameSave,
    private val restorePoint: RestorePoint = RestorePoint.empty
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
        isExecuting = true
        startTimeInSeconds = System.currentTimeMillis() / MILLISECONDS_PER_SECOND

        val ending = story.begin(
            StoryBeginParameters(
                Flags.fromMap(restorePoint.flags),
                restorePoint.storyRestorePoint,
                gameConfiguration.gameAdapter.storyAdapter,
                gameConfiguration.stepTracker,
                cancellationToken
            )
        )

        try {
            lock.lock()

            if (ending != Ending.noEnding && !endingsReached.contains(ending)) {
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
        return RestorePoint(
            name,
            story.flags.toMap(),
            story.createRestorePoint()
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

        var endings: List<Ending>

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
