package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.StepResult

/**
 * Provides an interface for interactive components.
 */
public interface InteractiveComponent {
    /**
     * Invoke the interactive component. Any arguments can be specified with [args], as well as [flags]. A
     * [cancellationToken] can be specified to allow cancellation. Returns a [StepResult].
     */
    public operator fun invoke(args: Array<String>, flags: Flags, cancellationToken: CancellationToken): StepResult
}
