package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterTest {
    private val speaks = object : Speaks {
        override fun invoke(character: Character, line: String) {
            println("${character.name}: $line")
        }
    }

    private val emotionChanged = object : Emotes {
        override fun invoke(character: Character, emotion: Emotion) {
            println("${character.name} looks $emotion.")
        }
    }

    @Test
    fun `given a character named Test then name is assigned`() {
        // Given
        val character = Character("Test", speaks, emotionChanged)

        // Then
        Assertions.assertEquals("Test", character.name)
    }

    @Test
    fun `given a character when assigning emotion then emotion is assigned`() {
        // Given
        val character = Character("", speaks, emotionChanged)

        // When
        character looks happy

        // Then
        Assertions.assertEquals(happy, character.emotion)
    }
}
