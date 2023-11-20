package com.github.benpollarduk.ktvn.logic.adapters.standard

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a standard [LayoutAdapter] with a specified [gameEngine].
 */
internal class StandardLayoutAdapter(private val gameEngine: GameEngine) : LayoutAdapter {
    override val moveAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameEngine.acknowledgeLayoutMovement()
        }
    }

    override val moveListener: MoveListener = object : MoveListener {
        override fun move(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            acknowledgement: AcknowledgeListener
        ) {
            gameEngine.moveCharacter(character, fromPosition, toPosition)
            acknowledgement.waitFor()
        }
    }
}
