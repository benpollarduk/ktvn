package com.github.benpollarduk.ktvn.prototyping.swing.animations.scene

import com.github.benpollarduk.ktvn.prototyping.swing.animations.Animation
import com.github.benpollarduk.ktvn.prototyping.swing.ui.SceneCanvasPanel

/**
 * Provides a class to instantly display background.
 */
internal class InstantBackground(private val scene: SceneCanvasPanel) : Animation {
    override operator fun invoke(listener: () -> Unit) {
        scene.backgroundFadeOpacity = 1.0
        scene.reRender()
        listener()
    }
}
