package com.github.benpollarduk.ktvn.characters

/**
 * Provides an interface for objects that can handle a specified [character] speaking a [line].
 */
public interface Speaks {
    /**
     * Invoke the listener with a specified [character] and [line].
     */
    public operator fun invoke(character: Character, line: String)
}
