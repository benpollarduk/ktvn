package com.github.benpollarduk.ktvn.text.frames

/**
 * Defines the position of a [Char] in 2D space, with a specified [column] and [row].
 */
public data class CharacterPosition(
    public val character: Char,
    public val column: Int,
    public val row: Int
)
