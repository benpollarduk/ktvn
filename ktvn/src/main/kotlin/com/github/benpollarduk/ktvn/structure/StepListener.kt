package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.logic.Flags

/**
 * Provides an interface for listeners for [Step] change events.
 */
public interface StepListener {
    /**
     * Invoke the listener to notify entry of a specified [step] and [flags], [canSkip] indicates if the step can be
     * skipped. A [cancellationToken] must be provided to support cancellation.
     */
    public fun enter(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken)

    /**
     * Invoke the listener to notify exit of a specified [step] with [flags].
     */
    public fun exit(step: Step, flags: Flags)
}
