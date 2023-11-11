package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.logic.configuration.LayoutConfiguration
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an [LayoutConfiguration] for an ANSI console.
 */
internal class AnsiConsoleLayoutConfiguration : LayoutConfiguration {
    override val moveAcknowledgementListener: AcknowledgeListener = object : AcknowledgeListener {
        override fun waitFor() {
            // pass through
        }
    }

    override val moveListener: MoveListener = object : MoveListener {
        override fun move(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            acknowledgement: AcknowledgeListener
        ) {
            println("${character.name} moves from '$fromPosition' to '$toPosition'.")
            acknowledgement.waitFor()
        }
    }
}
