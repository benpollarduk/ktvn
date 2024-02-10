package com.github.benpollarduk.ktvn.io.restore

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotions.happy
import com.github.benpollarduk.ktvn.layout.Positions
import com.github.benpollarduk.ktvn.setup.TestGameConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterRestorePointTest {
    @Test
    fun `given valid values when create then no exception`() {
        // Then
        Assertions.assertDoesNotThrow {
            // Given
            val character = Character("Name", TestGameConfiguration.gameAdapter.characterAdapter)
            val emotion = happy
            val position = Positions.left

            // When
            CharacterRestorePoint(character, emotion, position)
        }
    }
}
