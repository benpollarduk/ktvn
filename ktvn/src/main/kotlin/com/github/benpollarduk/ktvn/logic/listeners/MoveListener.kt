package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Position

/**
 * Provides an interface for listeners to [Layout] move events.
 */
public interface MoveListener {
    /**
     * Invoke the listener with a specified [character], [fromPosition], [toPosition] and [acknowledgement].
     */
    public fun move(
        character: Character,
        fromPosition: Position,
        toPosition: Position,
        acknowledgement: AcknowledgeListener
    )
}
