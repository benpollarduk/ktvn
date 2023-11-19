package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listeners for [Step] change events.
 */
public interface StepListener {
    /**
     * Invoke the listener to notify entry of a specified [step] an if it can be skipped. A [cancellationToken] must be
     * provided to support cancellation.
     */
    public fun enter(step: Step, canSkip: Boolean, cancellationToken: CancellationToken)

    /**
     * Invoke the listener to notify exit of a specified [step].
     */
    public fun exit(step: Step)
}
