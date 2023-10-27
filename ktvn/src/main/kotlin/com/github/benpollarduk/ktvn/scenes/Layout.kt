package com.github.benpollarduk.ktvn.scenes

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.scenes.Positions.above
import com.github.benpollarduk.ktvn.scenes.Positions.below
import com.github.benpollarduk.ktvn.scenes.Positions.center
import com.github.benpollarduk.ktvn.scenes.Positions.left
import com.github.benpollarduk.ktvn.scenes.Positions.leftOf
import com.github.benpollarduk.ktvn.scenes.Positions.right
import com.github.benpollarduk.ktvn.scenes.Positions.rightOf

/**
 * Provides a layout for a specified list of [positions].
 */
public class Layout(positions: List<CharacterPosition>) {
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
        mutablePositions.removeAll { it.character == character }
        mutablePositions.add(CharacterPosition(character, position))
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
