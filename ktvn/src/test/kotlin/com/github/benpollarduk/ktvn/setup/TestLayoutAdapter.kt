package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.logic.adapters.LayoutAdapter

internal class TestLayoutAdapter : LayoutAdapter {
    override val moveListener: MoveListener = object : MoveListener {
        override fun move(
            character: Character,
            fromPosition: Position,
            toPosition: Position,
            transition: LayoutTransition
        ) {
            // nothing
        }
    }
}
