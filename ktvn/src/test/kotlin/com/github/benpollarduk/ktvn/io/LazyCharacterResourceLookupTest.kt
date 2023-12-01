package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotions.happy
import com.github.benpollarduk.ktvn.setup.TestCharacterAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("MaxLineLength")
class LazyCharacterResourceLookupTest {
    @Test
    fun `given ben no variation normal emotion when get key then return ben-normal with png extension`() {
        // Given
        val character = Character("Ben", TestCharacterAdapter())
        val lookup = LazyCharacterResourceLookup(extension = ".png")

        // When
        val result = lookup.getKey(character)

        // Then
        Assertions.assertEquals("ben-normal.png", result)
    }

    @Test
    fun `given dave outdoor variation normal emotion when get key then return dave-outdoor-normal with png extension`() {
        // Given
        val character = Character("Dave", TestCharacterAdapter(), "Outdoor")
        val lookup = LazyCharacterResourceLookup(extension = ".png")

        // When
        val result = lookup.getKey(character)

        // Then
        Assertions.assertEquals("dave-outdoor-normal.png", result)
    }

    @Test
    fun `given gabby ski variation happy emotion when get key then return gabby-ski-happy with png extension`() {
        // Given
        val character = Character("Gabby", TestCharacterAdapter(), "Ski")
        character.looks(happy)
        val lookup = LazyCharacterResourceLookup(extension = ".png")

        // When
        val result = lookup.getKey(character)

        // Then
        Assertions.assertEquals("gabby-ski-happy.png", result)
    }

    @Test
    fun `given gabby ski variation happy emotion with root of characters when get key then return gabby-ski-happy with png extension`() {
        // Given
        val character = Character("Gabby", TestCharacterAdapter(), "Ski")
        character.looks(happy)
        val lookup = LazyCharacterResourceLookup("characters//", ".png")

        // When
        val result = lookup.getKey(character)

        // Then
        Assertions.assertEquals("characters//gabby-ski-happy.png", result)
    }
}
