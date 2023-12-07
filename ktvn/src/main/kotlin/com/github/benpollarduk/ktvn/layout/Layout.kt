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
import com.github.benpollarduk.ktvn.layout.transitions.Instant
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import java.util.concurrent.locks.ReentrantLock

/**
 * Provides a layout for positioning characters.
 */
@Suppress("TooManyFunctions")
public open class Layout private constructor(private val setup: (Layout) -> Unit) {
    private val mutablePositions: MutableList<CharacterPosition> = mutableListOf()
    private val lock = ReentrantLock()
    private var adapter: LayoutAdapter? = null
    private var transition: LayoutTransition = Instant()

    /**
     * Get the number of characters in this layout.
     */
    public val characters: Int
        get() {
            try {
                lock.lock()
                return mutablePositions.size
            } finally {
                lock.unlock()
            }
        }

    init {
        runSetup()
    }

    private fun runSetup() {
        setup(this)
    }

    /**
     * Clear this layout.
     */
    public fun clear() {
        try {
            lock.lock()
            mutablePositions.clear()
        } finally {
            lock.unlock()
        }
    }

    /**
     * Get an array of all [CharacterPosition] in this [Layout].
     */
    public fun toArrayOfCharacterPosition(): Array<CharacterPosition> {
        try {
            lock.lock()
            return mutablePositions.toTypedArray()
        } finally {
            lock.unlock()
        }
    }

    /**
     * Add a [character] to this [Layout] at a [position].
     */
    public fun add(character: Character, position: Position) {
        try {
            lock.lock()
            mutablePositions.removeAll { it.character == character }
            mutablePositions.add(CharacterPosition(character, position))
        } finally {
            lock.unlock()
        }
    }

    /**
     * Move a [character] to a [position] with a specified [transition].
     */
    public fun move(character: Character, position: Position, transition: LayoutTransition) {
        try {
            lock.lock()
            val current = mutablePositions.firstOrNull { it.character == character }
            val from = current?.position ?: none
            mutablePositions.removeAll { it.character == character }
            mutablePositions.add(CharacterPosition(character, position))
            val config = adapter ?: return
            config.moveListener.move(
                character,
                from,
                position,
                transition
            )
        } finally {
            lock.unlock()
        }
    }

    /**
     * Specify the [transition] to apply when moving characters.
     */
    public infix fun transition(transition: LayoutTransition) {
        this.transition = transition
    }

    /**
     * Specify the [adapter].
     */
    public infix fun configure(adapter: LayoutAdapter) {
        this.adapter = adapter
    }

    /**
     * Add a [character] in a hidden position.
     */
    public infix fun addHidden(character: Character) {
        move(character, none, transition)
    }

    /**
     * Add a [character] at the left of position.
     */
    public infix fun addLeftOf(character: Character) {
        move(character, leftOf, transition)
    }

    /**
     * Add a [character] at the above position.
     */
    public infix fun addAbove(character: Character) {
        move(character, above, transition)
    }

    /**
     * Add a [character] at the right of position.
     */
    public infix fun addRightOf(character: Character) {
        move(character, rightOf, transition)
    }

    /**
     * Add a [character] at the below position.
     */
    public infix fun addBelow(character: Character) {
        move(character, below, transition)
    }

    /**
     * Add a [character] at the left position.
     */
    public infix fun addLeft(character: Character) {
        move(character, left, transition)
    }

    /**
     * Add a [character] at the right position.
     */
    public infix fun addRight(character: Character) {
        move(character, right, transition)
    }

    /**
     * Add a [character] at the center position.
     */
    public infix fun addCenter(character: Character) {
        move(character, center, transition)
    }

    /**
     * Move a [character] to the left position.
     */
    public infix fun moveLeft(character: Character) {
        move(character, left, transition)
    }

    /**
     * Move a [character] to the center position.
     */
    public infix fun moveCenter(character: Character) {
        move(character, center, transition)
    }

    /**
     * Move a [character] to the right position.
     */
    public infix fun moveRight(character: Character) {
        move(character, right, transition)
    }

    /**
     * Exit a [character] to the left.
     */
    public infix fun exitLeft(character: Character) {
        move(character, leftOf, transition)
    }

    /**
     * Exit a [character] to the top.
     */
    public infix fun exitTop(character: Character) {
        move(character, above, transition)
    }

    /**
     * Exit a [character] to the right.
     */
    public infix fun exitRight(character: Character) {
        move(character, rightOf, transition)
    }

    /**
     * Exit a [character] to the bottom.
     */
    public infix fun exitBottom(character: Character) {
        move(character, below, transition)
    }

    /**
     * Make a [character] disappear.
     */
    public infix fun disappear(character: Character) {
        move(character, none, transition)
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
