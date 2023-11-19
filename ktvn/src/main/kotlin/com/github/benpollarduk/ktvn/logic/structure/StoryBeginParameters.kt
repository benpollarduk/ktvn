package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.io.restore.StoryRestorePoint
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration

/**
 * Provides parameters for beginning stories. The [storyRestorePoint] can be optionally specified.
 * A [storyConfiguration] must be provided to receive progression updates. A [stepTracker] must be provided to track
 * which steps have been seen. A [progressionMode] must be specified to determine how the story progresses.
 * A [cancellationToken] must be provided to allow for the story to be cancelled.
 */
internal data class StoryBeginParameters(
    internal val flags: Flags,
    internal val storyRestorePoint: StoryRestorePoint = StoryRestorePoint.start,
    internal val storyConfiguration: StoryConfiguration,
    internal val stepTracker: StepTracker,
    internal val progressionMode: ProgressionMode,
    internal val cancellationToken: CancellationToken
)
