package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion

/**
 * Provides an interface for objects that can handle [Emotion] changes.
 */
public interface Emotes {
    /**
     * Invoke the listener with a specified [character], [emotion] and [acknowledgement].
     */
    public operator fun invoke(character: Character, emotion: Emotion, acknowledgement: Acknowledges)
}
