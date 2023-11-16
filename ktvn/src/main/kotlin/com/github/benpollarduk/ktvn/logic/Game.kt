package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.Save
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Story
import java.util.concurrent.locks.ReentrantLock

/**
 * An executable game with a specified [story], [gameConfiguration] and optional [save].
 */
public class Game(
    private val story: Story,
    private val gameConfiguration: GameConfiguration,
    private val save: Save = Save.empty
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
            Flags.fromMap(save.flags),
            save.storyRestorePoint,
            gameConfiguration.storyConfiguration,
            cancellationToken
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
            GameExecutionResult(true, ending, getSave("Reached ending ${ending.name}"))
        }
    }

    /**
     * End execution of the game.
     */
    internal fun end() {
        cancellationToken.cancel()
    }

    /**
     * Get a [Save] for the game with a specified [name].
     */
    public fun getSave(name: String): Save {
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

        return Save(
            name,
            story.flags.toMap(),
            story.createRestorePoint(),
            save.totalSeconds + additionalTimeInSeconds,
            endings
        )
    }

    private companion object {
        private const val MILLISECONDS_PER_SECOND: Int = 1000
    }
}
