package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.backgrounds.StaticBackground
import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import com.github.benpollarduk.ktvn.characters.BaseEmotions.sad
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.logic.Act
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.scenes.CharacterPosition
import com.github.benpollarduk.ktvn.scenes.Layout
import com.github.benpollarduk.ktvn.scenes.Positions.leftOf
import com.github.benpollarduk.ktvn.scenes.Positions.rightOf
import com.github.benpollarduk.ktvn.scenes.Scene
import com.github.benpollarduk.ktvn.steps.Then.Companion.then
import com.github.benpollarduk.ktvn.steps.steps

public class ExampleScript {

    private val ben = Character("Ben")
    private val beth = Character("Beth")

    private fun scene1(): Scene {
        val positions = listOf(
            CharacterPosition(ben, leftOf),
            CharacterPosition(beth, rightOf),
        )

        val layout = Layout(positions)

        val steps = steps(
            then { ben looks sad },
            then { ben says "Where are you Beth?" },
            then { layout moveLeft ben },
            then { layout moveRight beth },
            then { beth says "I'm here!" },
            then { ben looks happy },
            then { beth looks happy },
        )

        return Scene(
            StaticBackground(),
            layout,
            steps,
        )
    }
    public fun runTestGame() {
        val act = Act(listOf(scene1()))
        val game = Game(listOf(act))
        game.begin()
    }
}
