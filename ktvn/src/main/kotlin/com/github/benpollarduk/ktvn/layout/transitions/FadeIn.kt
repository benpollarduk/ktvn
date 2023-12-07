package com.github.benpollarduk.ktvn.layout.transitions

/**
 * Provides a fade in transition between scenes. Fades in over a specified [durationInMs]
 */
public class FadeIn(public val durationInMs: Long) : LayoutTransition {
    override fun toString(): String {
        return "fade in"
    }
}
