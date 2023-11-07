package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Flags

/**
 * Provides an interface for a step in a [Scene].
 */
public interface Step {
    /**
     * Get the name of this [Step].
     */
    public val name: String

    /**
     * Invoke the step with the specified flags to return a [StepResult]. A [cancellationToken] must be provided to
     * allow for the story to be cancelled.
     */
    public operator fun invoke(flags: Flags = Flags.empty, cancellationToken: CancellationToken): StepResult
}
