package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listeners to acknowledgment requests.
 */
public interface AcknowledgeListener {
    /**
     * Wait for an acknowledgement.
     */
    public fun waitFor()
}
