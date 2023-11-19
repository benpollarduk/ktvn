package com.github.benpollarduk.ktvn.io.game

/**
 * Provides settings for a [Game].
 */
public data class GameSettings(
    public val skipUnseen: Boolean,
    public var autoPostDelayInMs: Long
) {
    public companion object {
        /**
         * Get default [GameSettings].
         */
        public val default: GameSettings = GameSettings(false, 500)
    }
}
