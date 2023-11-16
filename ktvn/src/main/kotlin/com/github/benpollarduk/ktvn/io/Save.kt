package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.logic.Ending

/**
 * Provides a save with a specified [name], [flags], [storyRestorePoint], [totalSeconds] and list of
 * [endingsReached].
 */
public data class Save(
    public val name: String,
    public val flags: Map<String, Boolean>,
    public val storyRestorePoint: StoryRestorePoint,
    public val totalSeconds: Long,
    public val endingsReached: List<Ending>
) {
    /**
     * Get the version of this save.
     */
    public val version: String = VERSION_1_0_0

    public companion object {
        /**
         * Get the version string for version 1.0.
         */
        public const val VERSION_1_0_0: String = "1.0.0"

        /**
         * Provides an empty save.
         */
        public val empty: Save = Save(
            "",
            emptyMap(),
            StoryRestorePoint.start,
            0,
            emptyList()
        )
    }
}
