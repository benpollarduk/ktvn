package com.github.benpollarduk.ktvn.logic.listeners

/**
 * Provides an interface for listeners to acknowledgment requests.
 */
public interface AcknowledgeListener {
    /**
     * Wait for an acknowledgement.
     */
    public fun waitFor()
}
