package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.characters.animations.Animation

/**
 * Provides an interface for listeners to animate events.
 */
public interface AnimateListener {
    /**
     * Invoke the listener with a specified [character] and [animation].
     */
    public fun animate(character: Character, animation: Animation)
}
