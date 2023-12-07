package com.github.benpollarduk.ktvn.prototyping.swing.animations.scene

import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.AnimationController
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel
import com.github.benpollarduk.ktvn.structure.transitions.FadeIn

/**
 * Provides a class to fade in a background.
 */
internal class FadeInBackground(private val scene: SceneCanvasPanel, private val fadeIn: FadeIn) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        scene.backgroundFadeColor = fadeIn.from
        scene.backgroundFadeOpacity = 0.0

        scene.reRender()

        val animationController = AnimationController(
            fadeIn.durationInMs,
            AnimationController.DEFAULT_FRAMES_PER_SECOND
        ) { progress ->
            scene.backgroundFadeOpacity = progress.value

            scene.reRender()

            if (progress.finished) {
                listener.invoke()
            }
        }

        animationController.start()
    }
}
