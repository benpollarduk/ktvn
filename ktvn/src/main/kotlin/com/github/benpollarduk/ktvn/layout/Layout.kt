package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Positions.above
import com.github.benpollarduk.ktvn.layout.Positions.below
import com.github.benpollarduk.ktvn.layout.Positions.center
import com.github.benpollarduk.ktvn.layout.Positions.left
import com.github.benpollarduk.ktvn.layout.Positions.leftOf
import com.github.benpollarduk.ktvn.layout.Positions.right
import com.github.benpollarduk.ktvn.layout.Positions.rightOf

/**
 * Provides a layout for a specified list of [positions].
 */
public class Layout(
    positions: List<CharacterPosition>,
    private val moves: Moves
) {
    private val mutablePositions: MutableList<CharacterPosition> = mutableListOf()
    init {
        positions.forEach {
            mutablePositions.add(it)
        }
    }

    /**
     * Add a [character] to this [Layout] at a [position].
     */
    public fun add(character: Character, position: Position) {
        mutablePositions.removeAll { it.character == character }
        mutablePositions.add(CharacterPosition(character, position))
    }

    /**
     * Move a [character] to a [position].
     */
    public fun move(character: Character, position: Position) {
        val current = mutablePositions.firstOrNull { it.character == character }
        val fromPosition = current?.position ?: left
        mutablePositions.removeAll { it.character == character }
        mutablePositions.add(CharacterPosition(character, position))
        moves(character, fromPosition, position)
    }

    /**
     * Move a [character] to the left position.
     */
    public infix fun moveLeft(character: Character) {
        move(character, left)
    }

    /**
     * Move a [character] to the center position.
     */
    public infix fun moveCenter(character: Character) {
        move(character, center)
    }

    /**
     * Move a [character] to the right position.
     */
    public infix fun moveRight(character: Character) {
        move(character, right)
    }

    /**
     * Exit a [character] to the left.
     */
    public infix fun exitLeft(character: Character) {
        move(character, leftOf)
    }

    /**
     * Exit a [character] to the top.
     */
    public infix fun exitTop(character: Character) {
        move(character, above)
    }

    /**
     * Exit a [character] to the right.
     */
    public infix fun exitRight(character: Character) {
        move(character, rightOf)
    }

    /**
     * Exit a [character] to the bottom.
     */
    public infix fun exitBottom(character: Character) {
        move(character, below)
    }
}
