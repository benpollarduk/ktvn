package com.github.benpollarduk.ktvn.prototyping.swing.animations

/**
 * An invocable animation.
 */
internal interface Animation {
    /**
     * Invoke the animation.
     */
    operator fun invoke(listener: () -> Unit)
}
