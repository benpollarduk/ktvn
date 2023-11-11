package com.github.benpollarduk.ktvn.characters

/**
 * Provides a collection of animations.
 */
public object Animations {
    /**
     * No animation.
     */
    public val nothing: Animation = object : Animation {
        override fun toString(): String {
            return "none"
        }
    }

    /**
     * Shaking.
     */
    public val shaking: Animation = object : Animation {
        override fun toString(): String {
            return "shake"
        }
    }

    /**
     * Sweating.
     */
    public val sweating: Animation = object : Animation {
        override fun toString(): String {
            return "sweat"
        }
    }
}
