package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an interface for listeners to narrate events.
 */
public interface NarrateListener {
    /**
     * Invoke the listener with a specified [narrator], [line] and [acknowledgement].
     */
    public fun narrate(narrator: Narrator, line: String, acknowledgement: AcknowledgeListener)
}
