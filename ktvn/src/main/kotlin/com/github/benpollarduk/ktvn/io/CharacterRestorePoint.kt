package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.layout.Position

/**
 * Provides a restore point for a [Character] with a specified [character], [emotion] and [position].
 */
public data class CharacterRestorePoint(
    public val character: Character,
    public val emotion: Emotion,
    public val position: Position
)
