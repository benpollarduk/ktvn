package com.github.benpollarduk.ktvn.logic

/**
 * Provides an interface for listeners to game execution events.
 */
public interface GameExecutionListener {
    /**
     * Invoke the listener for execute with a specified [result].
     */
    public fun finished(result: GameExecutionResult)
}
