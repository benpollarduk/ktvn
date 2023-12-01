package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion

/**
 * Provides an interface for a lookup for [Character] resources.
 */
public interface CharacterResourceLookup {
    /**
     * Get a key for a [character].
     */
    public fun getKey(character: Character): String

    /**
     * Get a key for a [character] and [emotion].
     */
    public fun getKey(character: Character, emotion: Emotion): String
}
