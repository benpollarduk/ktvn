package com.github.benpollarduk.ktvn.prototyping.swing.animations.layout

import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.transitions.FadeIn
import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.AnimationController
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel

/**
 * Provides a class to fade in a character.
 */
internal class FadeInCharacter(
    private val scene: SceneCanvasPanel,
    private val render: CharacterRender,
    private val position: Position,
    private val startOpacity: Double,
    private val endOpacity: Double,
    private val fadeIn: FadeIn
) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        render.opacity = startOpacity
        render.position = position
        render.currentX = position.normalizedX
        render.currentY = position.normalizedY

        val animationController = AnimationController(
            fadeIn.durationInMs,
            AnimationController.DEFAULT_FRAMES_PER_SECOND
        ) { progress ->
            render.opacity = startOpacity + ((endOpacity - startOpacity) * progress.value)

            scene.reRender()

            if (progress.finished) {
                listener.invoke()
            }
        }

        animationController.start()
    }
}
