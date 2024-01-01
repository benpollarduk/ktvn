package com.github.benpollarduk.ktvn.backgrounds

import java.awt.Color

/**
 * A background with a specified [color].
 */
public class ColorBackground(public val color: Color = Color.BLACK) : Background {
    public companion object {
        /**
         * Provides an empty background.
         */
        public val empty: ColorBackground = ColorBackground()

        /**
         * Create a background from a specified [color].
         */
        public infix fun backgroundFromColor(color: Color): ColorBackground {
            return ColorBackground(color)
        }
    }
}
