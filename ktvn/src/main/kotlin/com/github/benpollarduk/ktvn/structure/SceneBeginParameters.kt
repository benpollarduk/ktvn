package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.io.restore.SceneRestorePoint
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.Flags

/**
 * Provides parameters for beginning scenes. The [sceneRestorePoint] specifies where the scene restores from. A
 * [stepTracker] must be provided to track which steps have been seen. A [cancellationToken] must be provided to allow
 * for the scene to be cancelled.
 */
internal data class SceneBeginParameters(
    internal val flags: Flags,
    internal val sceneRestorePoint: SceneRestorePoint,
    internal val stepTracker: StepTracker,
    internal val cancellationToken: CancellationToken
)
