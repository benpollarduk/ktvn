package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import com.github.benpollarduk.ktvn.characters.BaseEmotions.sad
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.scenes.Act
import com.github.benpollarduk.ktvn.scenes.CharacterPosition
import com.github.benpollarduk.ktvn.scenes.Game
import com.github.benpollarduk.ktvn.scenes.Layout
import com.github.benpollarduk.ktvn.scenes.Positions.leftOf
import com.github.benpollarduk.ktvn.scenes.Positions.rightOf
import com.github.benpollarduk.ktvn.scenes.Scene
import com.github.benpollarduk.ktvn.scenes.backgrounds.StaticBackground

public class ExampleScript {

    private val ben = Character("Ben")
    private val beth = Character("Beth")
    private fun scene1(): Scene {
        val positions = listOf(
            CharacterPosition(ben, leftOf),
            CharacterPosition(beth, rightOf)
        )

        return Scene(StaticBackground(), Layout(positions)) { layout ->
            ben looks sad
            ben says "Where are you Beth?"
            layout moveLeft ben
            layout moveRight beth
            beth says "I'm here!"
            ben looks happy
            beth looks happy
        }
    }
    public fun runTestGame() {
        val act = Act(listOf(scene1()))
        val game = Game(listOf(act))
        game.begin()
    }
}
