package com.github.benpollarduk.ktvn.io

/**
 * Provides a restore point for a [Chapter] with a specified [sceneRestorePoint] and [scene].
 */
public data class ChapterRestorePoint(
    public val sceneRestorePoint: SceneRestorePoint,
    public val scene: Int
) {
    public companion object {
        /**
         * Provides a restore point at the start of a [Chapter].
         */
        public val start: ChapterRestorePoint = ChapterRestorePoint(SceneRestorePoint.start, 0)
    }
}
