package com.github.benpollarduk.ktvn.io.game

import com.github.benpollarduk.ktvn.logic.Ending

/**
 * Provides a game save with [totalSeconds] and list of [endingsReached]. Optionally [settings] can be provided. If no
 * value is specified [GameSettings.default] will be used.
 */
public class GameSave(
    public val totalSeconds: Long,
    public val endingsReached: List<Ending>,
    public val settings: GameSettings = GameSettings.default
) {
    /**
     * Get the version of this [GameSave].
     */
    public val version: String = VERSION_1_0_0

    public companion object {
        /**
         * Get the version string for version 1.0.
         */
        public const val VERSION_1_0_0: String = "1.0.0"

        /**
         * Provides an empty [GameSave].
         */
        public val empty: GameSave = GameSave(
            0,
            emptyList()
        )
    }
}
