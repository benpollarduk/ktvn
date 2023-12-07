package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import java.awt.image.BufferedImage

/**
 * Provides an interface for a visual scene.
 */
interface VisualScene {
    /**
     * Get if the text area is displayed.
     */
    var displayTextArea: Boolean

    /**
     * Get the sequenced text area.
     */
    val sequencedTextArea: SequencedTextArea

    /**
     * Get or set the desired resolution.
     */
    var desiredResolution: Resolution

    /**
     * Set the background [image] with a [transition]. The listener is invoked when the transition is complete.
     */
    fun setBackgroundImage(image: BufferedImage, transition: SceneTransition, listener: () -> Unit)

    /**
     * Add a [character] at a specified [position], represented with an [image].
     */
    fun addCharacter(character: Character, position: Position, image: BufferedImage)

    /**
     * Update a [character], represented with an [image].
     */
    fun updateCharacter(character: Character, image: BufferedImage)

    /**
     * Get if this [VisualScene] contains a [character].
     */
    fun containsCharacter(character: Character): Boolean

    /**
     * Move a [character] from a [from] position to a [to] position and a [transition]. The listener is invoked when
     * the transition is complete.
     */
    fun moveCharacter(
        character: Character,
        from: Position,
        to: Position,
        transition: LayoutTransition,
        listener: () -> Unit
    )

    /**
     * Perform an [animation] on a [character]. The listener is invoked when the animation is complete.
     */
    fun animateCharacter(character: Character, animation: Animation, listener: () -> Unit)

    /**
     * Reset.
     */
    fun reset()

    /**
     * Clear visuals.
     */
    fun clearVisuals()
}
