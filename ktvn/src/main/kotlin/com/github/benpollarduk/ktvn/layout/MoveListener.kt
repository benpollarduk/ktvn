package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides an interface for listeners to move events.
 */
public interface MoveListener {
    /**
     * Invoke the listener with a specified [character], [fromPosition] and [toPosition].
     */
    public fun move(
        character: Character,
        fromPosition: Position,
        toPosition: Position
    )
}
