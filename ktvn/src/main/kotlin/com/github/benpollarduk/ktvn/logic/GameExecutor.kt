package com.github.benpollarduk.ktvn.logic

import java.util.concurrent.locks.ReentrantLock

/**
 * Provides an object that controls execution of games.
 */
public object GameExecutor {
    private val executingGames: MutableList<Game> = mutableListOf()
    private val lock = ReentrantLock()

    /**
     * Execute a [game].
     */
    public fun execute(game: Game) {
        try {
            lock.lock()
            executingGames.add(game)
        } finally {
            lock.unlock()
        }

        game.execute()

        try {
            lock.lock()
            executingGames.remove(game)
        } finally {
            lock.unlock()
        }
    }

    /**
     * Execute a [game] asynchronously.
     */
    public fun executeAysnc(game: Game) {
        val gameThread = Thread { execute(game) }
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
