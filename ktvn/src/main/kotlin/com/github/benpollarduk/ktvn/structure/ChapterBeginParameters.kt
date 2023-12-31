package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.io.restore.ChapterRestorePoint
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.Flags

/**
 * Provides parameters for beginning chapters. The [chapterRestorePoint] specifies where the chapter restores from.
 * A [stepTracker] must be provided to track which steps have been seen. A [cancellationToken] must be provided to
 * allow for the chapter to be cancelled.
 */
internal data class ChapterBeginParameters(
    internal val flags: Flags,
    internal val chapterRestorePoint: ChapterRestorePoint,
    internal val stepTracker: StepTracker,
    internal val cancellationToken: CancellationToken
)
