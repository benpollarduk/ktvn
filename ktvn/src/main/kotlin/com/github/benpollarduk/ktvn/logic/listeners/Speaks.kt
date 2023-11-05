package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides an interface for objects that can handle speech.
 */
public interface Speaks {
    /**
     * Invoke the listener with a specified [character], [line] and [acknowledgment].
     */
    public operator fun invoke(character: Character, line: String, acknowledgement: Acknowledges)
}
