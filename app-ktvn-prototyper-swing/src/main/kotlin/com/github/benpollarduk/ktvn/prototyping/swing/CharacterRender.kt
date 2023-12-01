package com.github.benpollarduk.ktvn.prototyping.swing

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Position
import java.awt.image.BufferedImage

/**
 * Provides a container for holding a render of a [character].
 */
data class CharacterRender(
    val character: Character,
    val image: BufferedImage,
    val position: Position
)
