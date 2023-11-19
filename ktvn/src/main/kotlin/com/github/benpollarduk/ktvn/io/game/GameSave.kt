package com.github.benpollarduk.ktvn.io.game

import com.github.benpollarduk.ktvn.logic.Ending

/**
 * Provides a game save with [totalSeconds] and list of [endingsReached].
 */
public data class GameSave(
    public val totalSeconds: Long,
    public val endingsReached: List<Ending>
) {
    /**
     * Get the version of this restorePoint.
     */
    public val version: String = VERSION_1_0_0

    public companion object {
        /**
         * Get the version string for version 1.0.
         */
        public const val VERSION_1_0_0: String = "1.0.0"

        /**
         * Provides an empty game save.
         */
        public val empty: GameSave = GameSave(
            0,
            emptyList()
        )
    }
}
