package com.github.benpollarduk.ktvn.backgrounds

import java.awt.Color

/**
 * An empty background with a specified [color].
 */
public class EmptyBackground(public val color: Color = Color.BLACK) : Background {
    public companion object {
        /**
         * Create an empty background with a [color].
         */
        public infix fun static(color: Color) : EmptyBackground {
            return EmptyBackground(color)
        }
    }
}
