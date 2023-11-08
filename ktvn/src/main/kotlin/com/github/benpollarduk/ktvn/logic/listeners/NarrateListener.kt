package com.github.benpollarduk.ktvn.logic.listeners

/**
 * Provides an interface for listeners to [Narrator] narrate events.
 */
public interface NarrateListener {
    /**
     * Invoke the listener with a specified [line] and [acknowledgement].
     */
    public fun narrate(line: String, acknowledgement: AcknowledgeListener)
}
