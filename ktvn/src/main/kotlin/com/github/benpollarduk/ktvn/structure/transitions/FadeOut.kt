package com.github.benpollarduk.ktvn.structure.transitions

import java.awt.Color

/**
 * Provides a fade out transition between scenes. Fades out to the [to] color over a specified [durationInMs]
 */
public class FadeOut(public val to: Color, public val durationInMs: Long) : SceneTransition {
    override fun toString(): String {
        return "fade out"
    }
}
