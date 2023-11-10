package com.github.benpollarduk.ktvn.layout

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Positions.above
import com.github.benpollarduk.ktvn.layout.Positions.below
import com.github.benpollarduk.ktvn.layout.Positions.center
import com.github.benpollarduk.ktvn.layout.Positions.left
import com.github.benpollarduk.ktvn.layout.Positions.leftOf
import com.github.benpollarduk.ktvn.layout.Positions.none
import com.github.benpollarduk.ktvn.layout.Positions.right
import com.github.benpollarduk.ktvn.layout.Positions.rightOf
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a layout for positioning characters.
 */
@Suppress("TooManyFunctions")
public class Layout private constructor(setup: (Layout) -> Unit) {
    private val mutablePositions: MutableList<CharacterPosition> = mutableListOf()

    private var moveListener: MoveListener = object : MoveListener {
        override fun move(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            acknowledgement: AcknowledgeListener
        ) {
            // nothing
        }
    }

    private var moveAcknowledgement: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // nothing
        }
    }

    /**
     * Get the number of characters in this layout.
     */
    public val characters: Int
        get() = mutablePositions.size

    init {
        setup(this)
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
        val fromPosition = current?.position ?: none
        mutablePositions.removeAll { it.character == character }
        mutablePositions.add(CharacterPosition(character, position))
        moveListener.move(character, fromPosition, position, moveAcknowledgement)
    }

    /**
     * Specify how moveListener should be handled.
     */
    public infix fun setMoves(moveListener: MoveListener) {
        this.moveListener = moveListener
    }

    /**
     * Specify how move acknowledgments should be handled.
     */
    public infix fun setMoveAcknowledgments(moveAcknowledgement: AcknowledgeListener) {
        this.moveAcknowledgement = moveAcknowledgement
    }

    /**
     * Add a [character] at the left of position.
     */
    public infix fun addLeftOf(character: Character) {
        move(character, leftOf)
    }

    /**
     * Add a [character] at the above position.
     */
    public infix fun addAbove(character: Character) {
        move(character, above)
    }

    /**
     * Add a [character] at the right of position.
     */
    public infix fun addRightOf(character: Character) {
        move(character, rightOf)
    }

    /**
     * Add a [character] at the below position.
     */
    public infix fun addBelow(character: Character) {
        move(character, below)
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

    /**
     * Make a [character] disappear.
     */
    public infix fun disappear(character: Character) {
        move(character, none)
    }

    public companion object {
        /**
         * Create a new [Layout] with a specified [setup].
         */
        public infix fun createLayout(setup: (Layout) -> Unit): Layout {
            return Layout(setup)
        }
    }
}
