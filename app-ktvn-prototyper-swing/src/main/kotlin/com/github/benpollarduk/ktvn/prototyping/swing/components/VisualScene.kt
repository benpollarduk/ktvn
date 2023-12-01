package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.Resolution
import java.awt.image.BufferedImage

/**
 * Provides an interface for a visual scene.
 */
interface VisualScene {
    /**
     * Get or set the desired resolution.
     */
    var desiredResolution: Resolution

    /**
     * Set the background [image].
     */
    fun setBackgroundImage(image: BufferedImage)

    /**
     * Add or update a [character] represented with an [image].
     */
    fun addOrUpdate(character: Character, image: BufferedImage)

    /**
     * Move a [character] from a [from] position to a [to] position.
     */
    fun moveCharacter(character: Character, from: Position, to: Position)

    /**
     * Perform an [animation] on a [character].
     */
    fun animateCharacter(character: Character, animation: Animation)

    /**
     * Reset.
     */
    fun reset()
}
