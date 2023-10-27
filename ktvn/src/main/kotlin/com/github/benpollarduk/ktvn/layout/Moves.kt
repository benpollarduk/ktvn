package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides an interface for objects that can handle a movement.
 */
public interface Moves {
    /**
     * Invoke the listener with a specified [character], [fromPosition] and [toPosition].
     */
    public operator fun invoke(character: Character, fromPosition: Position, toPosition: Position)
}
