package com.github.benpollarduk.ktvn.structure.transitions

import java.awt.Color

/**
 * Provides a fade in transition between scenes. Fades in from the [from] color over a specified [durationInMs]
 */
public class FadeIn(public val from: Color, public val durationInMs: Long) : SceneTransition {
    override fun toString(): String {
        return "fade in"
    }
}
