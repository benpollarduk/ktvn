package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import com.github.benpollarduk.ktvn.characters.BaseEmotions.sad
import com.github.benpollarduk.ktvn.characters.Character
import scenes.Positions.center
import scenes.Positions.leftOf
import scenes.Positions.right
import scenes.Positions.rightOf
import scenes.Scene

public class ExampleScript {
    public fun test() {
        val garden = Scene()
        val ben = Character("Ben")
        val beth = Character("Beth")

        garden.enter(ben, leftOf, center)

        ben looks sad
        ben says "Where are you Beth?"

        garden moveLeft ben
        garden.enter(beth, rightOf, right)

        beth says "I'm here!"
        ben looks happy
        beth looks happy
    }
}
