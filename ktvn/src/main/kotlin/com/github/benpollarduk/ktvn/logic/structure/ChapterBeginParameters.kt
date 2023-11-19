package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.io.restore.ChapterRestorePoint
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.ProgressionMode

/**
 * Provides parameters for beginning chapters. The [chapterRestorePoint] specifies where the chapter restores from.
 * The [chapterListener] allows events to be invoked for this chapter. A [stepTracker] must be provided to track which
 * steps have been seen. A [progressionMode] must be specified to determine how the story progresses. A
 * [cancellationToken] must be provided to allow for the chapter to be cancelled.
 */
internal data class ChapterBeginParameters(
    internal val chapterRestorePoint: ChapterRestorePoint,
    internal val sceneListener: SceneListener,
    internal val chapterListener: ChapterListener,
    internal val stepTracker: StepTracker,
    internal val progressionMode: ProgressionMode,
    internal val cancellationToken: CancellationToken
)
