package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an interface for listeners to animate events.
 */
public interface AnimateListener {
    /**
     * Invoke the listener with a specified [character], [animation] and [acknowledgement].
     */
    public fun animate(character: Character, animation: Animation, acknowledgement: AcknowledgeListener)
}
