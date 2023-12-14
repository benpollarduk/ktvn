package com.github.benpollarduk.ktvn.prototyping.swing.animations.character

import com.github.benpollarduk.ktvn.characters.animations.Shake
import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.AnimationController
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel

/**
 * Provides a class to make a character shake.
 */
internal class ShakeAnimation(
    private val scene: SceneCanvasPanel,
    private val render: CharacterRender,
    private val shake: Shake
) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        render.animationController?.stop()
        val startX = render.currentX
        val startY = render.currentY
        val positionsX = listOf(
            startX - (FULL_MOVE * shake.strengthX),
            startX + (FULL_MOVE * shake.strengthX)
        )
        val positionsY = listOf(
            startY - (FULL_MOVE * shake.strengthY),
            startY + (FULL_MOVE * shake.strengthY)
        )

        val totalMoves = shake.oscillations * 2
        val msPerFrame = (AnimationController.MILLISECONDS_PER_SECOND / shake.framesPerSecond)
        val duration = msPerFrame * totalMoves
        var i = 0

        val animationController = AnimationController(
            duration.toLong(),
            AnimationController.DEFAULT_FRAMES_PER_SECOND
        ) { progress ->
            render.currentX = positionsX[i]
            render.currentY = positionsY[i]
            if (i == 1) {
                i = 0
            } else {
                i++
            }

            scene.reRender()

            if (progress.finished) {
                listener.invoke()
            }
        }

        render.animationController = animationController

        animationController.start()
    }

    companion object {
        /**
         * Get the value for a full move.
         */
        const val FULL_MOVE: Double = 0.05
    }
}
