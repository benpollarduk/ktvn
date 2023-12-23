package com.github.benpollarduk.ktvn.characters

/**
 * Provides an interface for listeners to think events.
 */
public fun interface ThinkListener {
    /**
     * Invoke the listener with a specified [character] and [line].
     */
    public fun think(character: Character, line: String)
}
