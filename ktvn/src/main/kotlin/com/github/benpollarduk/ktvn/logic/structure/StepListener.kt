package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listeners for [Step] change events.
 */
public interface StepListener {
    /**
     * Invoke the listener to notify entry of a specified [step].
     */
    public fun enter(step: Step)

    /**
     * Invoke the listener to notify exit of a specified [step].
     */
    public fun exit(step: Step)
}
