package com.github.benpollarduk.ktvn.rendering.text.frames

/**
 * Defines the position of a [Char] in 2D space, with a specified [column] and [row].
 */
public data class CharacterPosition(
    public val c: Char,
    public val column: Int,
    public val row: Int
)
