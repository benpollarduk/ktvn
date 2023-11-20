package com.github.benpollarduk.ktvn.io.restore

/**
 * Provides a restore point for a [Story] with a specified [chapterRestorePoint] and [chapter].
 */
public data class StoryRestorePoint(
    public val chapterRestorePoint: ChapterRestorePoint,
    public val chapter: Int
) {
    public companion object {
        /**
         * Provides a restore point at the start of a [Story].
         */
        public val start: StoryRestorePoint = StoryRestorePoint(ChapterRestorePoint.start, 0)
    }
}
