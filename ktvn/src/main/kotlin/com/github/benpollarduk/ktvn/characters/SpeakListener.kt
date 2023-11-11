package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an interface for listeners to speak events.
 */
public interface SpeakListener {
    /**
     * Invoke the listener with a specified [character], [line] and [acknowledgement].
     */
    public fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener)
}
