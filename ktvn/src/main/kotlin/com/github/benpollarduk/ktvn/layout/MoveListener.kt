package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an interface for listeners to move events.
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
