package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint

/**
 * Provides a result of the execution of a [Game].
 */
public data class GameExecutionResult(
    public val reachedEnding: Boolean,
    public val ending: Ending,
    public val gameSave: GameSave
) {
    public companion object {
        /**
         * Provides a default result for when a [Game] is cancelled.
         */
        public val cancelled: GameExecutionResult = GameExecutionResult(
            false,
            Ending.noEnding,
            GameSave.empty
        )
    }
}
