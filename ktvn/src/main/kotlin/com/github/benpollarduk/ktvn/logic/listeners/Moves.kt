package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Position

/**
 * Provides an interface for objects that can handle movements.
 */
public interface Moves {
    /**
     * Invoke the listener with a specified [character], [fromPosition], [toPosition] and [acknowledgement].
     */
    public operator fun invoke(
        character: Character,
        fromPosition: Position,
        toPosition: Position,
        acknowledgement: Acknowledges
    )
}
