package com.github.benpollarduk.ktvn.prototyping.swing.animations.layout

import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.transitions.Slide
import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.AnimationController
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel

/**
 * Provides a class to slide a character.
 */
internal class SlideCharacter(
    private val scene: SceneCanvasPanel,
    private val render: CharacterRender,
    private val startPosition: Position,
    private val endPosition: Position,
    private val slide: Slide
) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        val startX = startPosition.normalizedX
        val startY = startPosition.normalizedY
        val endX = endPosition.normalizedX
        val endY = endPosition.normalizedY

        render.currentX = startX
        render.currentY = startY
        render.position = endPosition
        render.opacity = 1.0

        scene.reRender()

        val animationController = AnimationController(
            slide.durationInMs,
            AnimationController.DEFAULT_FRAMES_PER_SECOND
        ) { progress ->
            render.currentX = startX + (endX - startX) * progress.value
            render.currentY = startY + (endY - startY) * progress.value

            scene.reRender()

            if (progress.finished) {
                listener.invoke()
            }
        }

        animationController.start()
    }
}
