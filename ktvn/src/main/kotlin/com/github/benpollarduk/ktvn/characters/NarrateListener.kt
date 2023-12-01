package com.github.benpollarduk.ktvn.characters

/**
 * Provides an interface for listeners to narrate events.
 */
public interface NarrateListener {
    /**
     * Invoke the listener with a specified [narrator] and [line].
     */
    public fun narrate(narrator: Narrator, line: String)
}
