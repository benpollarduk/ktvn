package com.github.benpollarduk.ktvn.logic

import java.util.concurrent.locks.ReentrantLock

/**
 * Provides an object that controls execution of games.
 */
public object GameExecutor {
    private val executingGames: MutableList<Game> = mutableListOf()
    private val lock = ReentrantLock()

    /**
     * Execute a [game]. Returns a [GameExecutionResult].
     */
    public fun execute(game: Game): GameExecutionResult {
        try {
            lock.lock()
            executingGames.add(game)
        } finally {
            lock.unlock()
        }

        val result = game.execute()

        try {
            lock.lock()
            executingGames.remove(game)
        } finally {
            lock.unlock()
        }

        return result
    }

    /**
     * Execute a [game] asynchronously. Optionally a [listener] can be provided to listen for the result of the
     * execution.
     */
    public fun executeAysnc(game: Game, listener: ((result: GameExecutionResult) -> Unit)?) {
        val gameThread = Thread {
            val result = execute(game)
            listener?.invoke(result)
        }
        gameThread.start()
    }

    /**
     * Cancel execution of all executing games.
     */
    public fun cancel() {
        try {
            lock.lock()
            executingGames.forEach { cancel(it) }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Cancel execution of a [game].
     */
    public fun cancel(game: Game) {
        game.end()
    }
}
