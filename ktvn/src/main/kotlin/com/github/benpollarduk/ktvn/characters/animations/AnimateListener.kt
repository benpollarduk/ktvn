package com.github.benpollarduk.ktvn.characters.animations

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides an interface for listeners to animate events.
 */
public fun interface AnimateListener {
    /**
     * Invoke the listener with a specified [character] and [animation].
     */
    public fun animate(character: Character, animation: Animation)
}
