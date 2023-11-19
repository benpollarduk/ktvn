package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.io.restore.SceneRestorePoint
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.ProgressionMode

/**
 * Provides parameters for beginning scenes. The [sceneRestorePoint] specifies where the scene restores from. The
 * [sceneListener] allows events to be invoked for this scene. A [stepTracker] must be provided to track which steps
 * have been seen. A [progressionMode] must be specified to determine how the story progresses. A [cancellationToken]
 * must be provided to allow for the scene to be cancelled.
 */
internal data class SceneBeginParameters(
    internal val sceneRestorePoint: SceneRestorePoint,
    internal val sceneListener: SceneListener,
    internal val stepTracker: StepTracker,
    internal val progressionMode: ProgressionMode,
    internal val cancellationToken: CancellationToken
)
