package com.github.benpollarduk.ktvn.logic.adapters.dynamic

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.engines.GameEngine

/**
 * Provides a [LayoutAdapter] with a [gameEngine] that can be specified after initialization.
 */
internal class DynamicLayoutAdapter(internal var gameEngine: GameEngine? = null) : LayoutAdapter {
    override val moveListener: MoveListener = object : MoveListener {
        override fun move(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            transition: LayoutTransition
        ) {
            gameEngine?.characterMoves(character, fromPosition, toPosition, transition)
        }
    }
}
