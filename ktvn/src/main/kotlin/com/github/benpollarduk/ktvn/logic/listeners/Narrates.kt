package com.github.benpollarduk.ktvn.logic.listeners

/**
 * Provides an interface for objects that can handle narration.
 */
public interface Narrates {
    /**
     * Invoke the listener with a specified [line] and [acknowledgement].
     */
    public operator fun invoke(line: String, acknowledgement: Acknowledges)
}
