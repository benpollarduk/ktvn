package com.github.benpollarduk.ktvn.io.restore

/**
 * Provides a restore point for a [Scene] with a specified list of [characterRestorePoints] and [step].
 */
public data class SceneRestorePoint(
    public val characterRestorePoints: List<CharacterRestorePoint>,
    public val step: Int
) {
    public companion object {
        /**
         * Provides a restore point at the start of a [Scene].
         */
        public val START: SceneRestorePoint = SceneRestorePoint(emptyList(), 1)
    }
}
