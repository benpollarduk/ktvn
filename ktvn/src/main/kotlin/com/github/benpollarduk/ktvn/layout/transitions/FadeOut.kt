package com.github.benpollarduk.ktvn.layout.transitions

/**
 * Provides a fade out transition between scenes. Fades out over a specified [durationInMs]
 */
public class FadeOut(public val durationInMs: Long) : LayoutTransition {
    override fun toString(): String {
        return "fade out"
    }
}
