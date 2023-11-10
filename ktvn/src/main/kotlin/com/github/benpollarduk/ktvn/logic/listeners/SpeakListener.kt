package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides an interface for listeners to speak events.
 */
public interface SpeakListener {
    /**
     * Invoke the listener with a specified [character], [line] and [acknowledgement].
     */
    public fun speak(character: Character, line: String, acknowledgement: AcknowledgeListener)
}
