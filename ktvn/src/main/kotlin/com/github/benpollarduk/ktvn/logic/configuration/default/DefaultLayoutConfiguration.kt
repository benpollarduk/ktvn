package com.github.benpollarduk.ktvn.logic.configuration.default

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.GameController
import com.github.benpollarduk.ktvn.logic.configuration.LayoutConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a default [LayoutConfiguration] with a specified [gameController].
 */
internal class DefaultLayoutConfiguration(private val gameController: GameController) : LayoutConfiguration {
    override val moveAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            gameController.acknowledgeLayoutMovement()
        }
    }

    override val moveListener: MoveListener = object : MoveListener {
        override fun move(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            acknowledgement: AcknowledgeListener
        ) {
            gameController.moveCharacter(character, fromPosition, toPosition)
            acknowledgement.waitFor()
        }
    }
}
