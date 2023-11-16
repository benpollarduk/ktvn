package com.github.benpollarduk.ktvn.text.frames

/**
 * Defines an interface for frames of text.
 */
public interface TextFrame {
    /**
     * Get an array of [CharacterPosition]. The elements are ordered in the desired order.
     */
    public fun getCharacterPositions(): List<CharacterPosition>
}
