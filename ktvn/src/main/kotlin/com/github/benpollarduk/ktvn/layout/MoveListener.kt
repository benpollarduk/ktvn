package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition

/**
 * Provides an interface for listeners to move events.
 */
public interface MoveListener {
    /**
     * Invoke the listener with a specified [character], [fromPosition] and [toPosition] with a specified [transition].
     */
    public fun move(
        character: Character,
        fromPosition: Position,
        toPosition: Position,
        transition: LayoutTransition
    )
}
