package com.github.benpollarduk.ktvn.layout.transitions

/**
 * Provides a slide transition between positions. Slides over a specified [durationInMs]
 */
public class Slide(public val durationInMs: Long) : LayoutTransition {
    override fun toString(): String {
        return "slide"
    }
}
