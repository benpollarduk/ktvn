package com.github.benpollarduk.ktvn.prototyping.swing.animations.character

import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel

/**
 * Provides a class to make a character stop.
 */
internal class StopAnimation(
    private val scene: SceneCanvasPanel,
    private val render: CharacterRender
) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        render.animationController?.stop()
        render.currentX = render.position.normalizedX
        render.currentY = render.position.normalizedY
        scene.reRender()
        listener.invoke()
    }
}
