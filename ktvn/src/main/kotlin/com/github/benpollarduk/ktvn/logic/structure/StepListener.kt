package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listeners for [Step] change events.
 */
public interface StepListener {
    /**
     * Invoke the listener to notify entry of a specified [step] an if it can be skipped.
     */
    public fun enter(step: Step, canSkip: Boolean)

    /**
     * Invoke the listener to notify exit of a specified [step].
     */
    public fun exit(step: Step)
}
