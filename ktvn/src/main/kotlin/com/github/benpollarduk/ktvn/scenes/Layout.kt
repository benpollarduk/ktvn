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
 * Provides a layout.
 */
public class Layout(positions: List<CharacterPosition>) {
    private val mutablePositions: MutableList<CharacterPosition> = mutableListOf()
    init {
        positions.forEach {
            mutablePositions.add(it)
        }
    }

    public fun add(character: Character, position: Position) {
        mutablePositions.removeAll { it.character == character }
        mutablePositions.add(CharacterPosition(character, position))
    }

    public fun move(character: Character, to: Position) {
        mutablePositions.removeAll { it.character == character }
        mutablePositions.add(CharacterPosition(character, to))
    }

    public infix fun moveLeft(character: Character) {
        move(character, left)
    }

    public infix fun moveCenter(character: Character) {
        move(character, center)
    }

    public infix fun moveRight(character: Character) {
        move(character, right)
    }

    public fun exit(character: Character, to: Position) {
        println(to)
        mutablePositions.removeAll { it.character == character }
    }

    public infix fun exitLeft(character: Character) {
        exit(character, leftOf)
    }

    public infix fun exitTop(character: Character) {
        exit(character, above)
    }

    public infix fun exitRight(character: Character) {
        exit(character, rightOf)
    }

    public infix fun exitDown(character: Character) {
        exit(character, below)
    }
}
