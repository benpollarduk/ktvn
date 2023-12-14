package com.github.benpollarduk.ktvn.prototyping.swing.animations.character

import com.github.benpollarduk.ktvn.characters.animations.Laugh
import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.AnimationController
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel

/**
 * Provides a class to make a character laugh.
 */
internal class LaughAnimation(
    private val scene: SceneCanvasPanel,
    private val render: CharacterRender,
    private val laugh: Laugh
) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        render.animationController?.stop()
        val start = render.currentY
        val positions = listOf(
            start + (FULL_MOVE * laugh.strength),
            start
        )

        val totalMoves = laugh.oscillations * 2
        val msPerFrame = (AnimationController.MILLISECONDS_PER_SECOND / laugh.framesPerSecond)
        val duration = msPerFrame * totalMoves
        var i = 0

        val animationController = AnimationController(
            duration.toLong(),
            AnimationController.DEFAULT_FRAMES_PER_SECOND
        ) { progress ->
            render.currentY = positions[i]
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
