package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.characters.BaseEmotions.happy
import org.junit.jupiter.api.Assertions

class CharacterTest {
    fun `given a character named Test then name is assigned`() {
        // Given
        val character = Character("Test")

        // Then
        Assertions.assertEquals("Test", character.name)
    }

    fun `given a character when assigning emotion then emotion is assigned`() {
        // Given
        val character = Character("")

        // When
        character looks happy

        // Then
        Assertions.assertEquals(happy, character.emotion)
    }
}
