package com.github.benpollarduk.ktvn.prototyping.swing

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.prototyping.swing.animations.AnimationController
import java.awt.image.BufferedImage

/**
 * Provides a container for holding a render of a [character].
 */
data class CharacterRender(
    val character: Character,
    var image: BufferedImage,
    var position: Position,
    var currentX: Double,
    var currentY: Double,
    var opacity: Double = 1.0,
    var animationController: AnimationController? = null
)
