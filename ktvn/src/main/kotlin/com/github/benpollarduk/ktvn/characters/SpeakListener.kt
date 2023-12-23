package com.github.benpollarduk.ktvn.characters

/**
 * Provides an interface for listeners to speak events.
 */
public fun interface SpeakListener {
    /**
     * Invoke the listener with a specified [character] and [line].
     */
    public fun speak(character: Character, line: String)
}
