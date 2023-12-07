package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.io.restore.StoryRestorePoint
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter

/**
 * Provides parameters for beginning stories. The [storyRestorePoint] can be optionally specified.
 * A [storyAdapter] must be provided to receive progression updates. A [stepTracker] must be provided to track
 * which steps have been seen. A [cancellationToken] must be provided to allow for the story to be cancelled.
 */
internal data class StoryBeginParameters(
    internal val flags: Flags,
    internal val storyRestorePoint: StoryRestorePoint = StoryRestorePoint.START,
    internal val storyAdapter: StoryAdapter,
    internal val stepTracker: StepTracker,
    internal val cancellationToken: CancellationToken
)
