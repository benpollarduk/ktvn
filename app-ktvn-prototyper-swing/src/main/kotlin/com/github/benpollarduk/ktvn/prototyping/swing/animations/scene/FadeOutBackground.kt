package com.github.benpollarduk.ktvn.prototyping.swing.animations.scene

import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.AnimationController
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel
import com.github.benpollarduk.ktvn.structure.transitions.FadeOut

/**
 * Provides a class to fade out a background.
 */
internal class FadeOutBackground(private val scene: SceneCanvasPanel, private val fadeOut: FadeOut) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        scene.backgroundFadeColor = fadeOut.to
        scene.backgroundFadeOpacity = 1.0

        scene.reRender()

        val animationController = AnimationController(
            fadeOut.durationInMs,
            AnimationController.DEFAULT_FRAMES_PER_SECOND
        ) { progress ->
            scene.backgroundFadeOpacity = 1.0 - progress.value

            scene.reRender()

            if (progress.finished) {
                listener.invoke()
            }
        }

        animationController.start()
    }
}
