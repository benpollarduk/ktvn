package com.github.benpollarduk.ktvn.logic.listeners

/**
 * Provides an interface that can handle acknowledgments.
 */
public interface Acknowledges {
    /**
     * Wait for an acknowledgement.
     */
    public fun waitFor()
}
