package com.github.benpollarduk.ktvn.characters

/**
 * Provides a collection of animations.
 */
public object Animations {
    /**
     * Base animation for nothing.
     */
    public val nothing: Animation = object : Animation {
        override fun toString(): String {
            return "none"
        }
    }

    /**
     * Base animation for shaking.
     */
    public val shaking: Animation = object : Animation {
        override fun toString(): String {
            return "shake"
        }
    }

    /**
     * Base animation for sweating.
     */
    public val sweating: Animation = object : Animation {
        override fun toString(): String {
            return "sweat"
        }
    }
}
