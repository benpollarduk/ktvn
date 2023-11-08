package com.github.benpollarduk.ktvn.logic.listeners

/**
 * Provides an interface for listeners to [Narrator] narrate events.
 */
public interface NarrateListener {
    /**
     * Invoke the listener with a specified [line] and [acknowledgement].
     */
    public operator fun invoke(line: String, acknowledgement: AcknowledgeListener)
}
