package com.github.benpollarduk.ktvn.logic

/**
 * Provides an interface for a step in a [Scene].
 */
public interface Step {
    /**
     * Invoke the step with the specified flags.
     */
    public operator fun invoke(flags: Flags = Flags.empty)
}
