package com.github.benpollarduk.ktvn.text.log

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides an interface for log elements.
 */
public sealed interface LogElement {
    /**
     * Create a log with a specified [character] and [value].
     */
    public data class CharacterLog(public val character: Character, public val value: String) : LogElement

    /**
     * Create a log with a specified [value].
     */
    public data class StringLog(public val value: String) : LogElement
}
