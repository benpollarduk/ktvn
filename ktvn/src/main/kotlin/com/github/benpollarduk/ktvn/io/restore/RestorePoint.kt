package com.github.benpollarduk.ktvn.io.restore

import java.time.LocalDateTime

/**
 * Provides a restore point with a specified [name], [flags] and [storyRestorePoint]. Optionally a [creationDate] and
 * [thumbnail] can be specified.
 */
public data class RestorePoint(
    public val name: String,
    public val flags: Map<String, Boolean>,
    public val storyRestorePoint: StoryRestorePoint,
    public val creationDate: LocalDateTime = LocalDateTime.now(),
    public val thumbnail: Thumbnail = Thumbnail.none
) {
    /**
     * Get the version of this [RestorePoint].
     */
    public val version: String = VERSION_1_0_0

    public companion object {
        /**
         * Get the version string for version 1.0.
         */
        public const val VERSION_1_0_0: String = "1.0.0"

        /**
         * Provides an empty [RestorePoint].
         */
        public val empty: RestorePoint = RestorePoint(
            "",
            emptyMap(),
            StoryRestorePoint.start
        )
    }
}
