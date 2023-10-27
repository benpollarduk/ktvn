package com.github.benpollarduk.ktvn.example

import com.github.benpollarduk.ktvn.backgrounds.StaticBackground
import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import com.github.benpollarduk.ktvn.characters.BaseEmotions.sad
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotes
import com.github.benpollarduk.ktvn.characters.Narrates
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.characters.Speaks
import com.github.benpollarduk.ktvn.layout.CharacterPosition
import com.github.benpollarduk.ktvn.layout.Layout
import com.github.benpollarduk.ktvn.layout.Moves
import com.github.benpollarduk.ktvn.layout.Positions.leftOf
import com.github.benpollarduk.ktvn.layout.Positions.rightOf
import com.github.benpollarduk.ktvn.logic.Act
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.Scene
import com.github.benpollarduk.ktvn.logic.Story
import com.github.benpollarduk.ktvn.logic.Then.Companion.then
import com.github.benpollarduk.ktvn.logic.content

public class ExampleScript(
    speaks: Speaks,
    emotionChanged: Emotes,
    narrates: Narrates,
    private val moves: Moves
) {
    private val narrator = Narrator(narrates)
    private val ben = Character("Ben", speaks, emotionChanged)
    private val beth = Character("Beth", speaks, emotionChanged)

    private fun scene1(): Scene {
        val characterPositions = listOf(
            CharacterPosition(ben, leftOf),
            CharacterPosition(beth, rightOf)
        )

        val layout = Layout(characterPositions, moves)

        val content = content(
            then { narrator narrates "It was a dark and stormy night..." },
            then { ben looks sad },
            then { ben says "Where are you Beth?" },
            then { layout moveLeft ben },
            then { layout moveRight beth },
            then { beth says "I'm here!" },
            then { ben looks happy },
            then { beth looks happy },
            then { narrator narrates "And that was the end of that!" }
        )

        return Scene(
            StaticBackground(),
            layout,
            content
        )
    }
    public fun begin() {
        val act = Act(listOf(scene1()))
        val game = Story(listOf(act))
        game.begin(Flags.empty)
    }
}
