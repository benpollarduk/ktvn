package com.github.benpollarduk.ktvn.io.tracking

import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import com.github.benpollarduk.ktvn.structure.Step

/**
 * Provides an interface for tracking which [Steps] in a [Game] have been seen.
 */
public interface StepTracker {
    /**
     * Get if a [step] has been seen.
     */
    public fun hasBeenSeen(step: Step): Boolean

    /**
     * Register that a [step] has been seen.
     */
    public fun registerStepSeen(step: Step)

    /**
     * Restore from a [path].
     */
    public fun restore(path: String): LoadResult<StepTracker>

    /**
     * Persist to a [path].
     */
    public fun persist(path: String): SaveResult
}
