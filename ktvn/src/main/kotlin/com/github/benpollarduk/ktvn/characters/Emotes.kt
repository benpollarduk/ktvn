package com.github.benpollarduk.ktvn.characters

/**
 * Provides an interface for objects that can handle a specified [character] shows an [emotion].
 */
public interface Emotes {
    /**
     * Invoke the listener with a specified [character] and [emotion].
     */
    public operator fun invoke(character: Character, emotion: Emotion)
}
