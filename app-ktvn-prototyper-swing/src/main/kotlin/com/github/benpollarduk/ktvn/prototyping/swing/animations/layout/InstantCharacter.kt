package com.github.benpollarduk.ktvn.prototyping.swing.animations.layout

import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel

/**
 * Provides a class to instantly show a character.
 */
internal class InstantCharacter(
    private val scene: SceneCanvasPanel,
    private val render: CharacterRender,
    private val position: Position
) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        render.opacity = 1.0
        render.currentX = position.normalizedX
        render.currentY = position.normalizedY

        scene.reRender()
        listener.invoke()
    }
}
