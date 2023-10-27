package com.github.benpollarduk.ktvn.characters

/**
 * Provides an interface for objects that can handle narration.
 */
public interface Narrates {
    /**
     * Invoke the listener with a specified [line].
     */
    public operator fun invoke(line: String)
}
