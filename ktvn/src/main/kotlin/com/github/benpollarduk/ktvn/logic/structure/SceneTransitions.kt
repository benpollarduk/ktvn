package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides a collection of scene transitions.
 */
public object SceneTransitions {
    /**
     * Instant.
     */
    public val instant: SceneTransition = object : SceneTransition {
        override fun toString(): String {
            return "instant"
        }
    }

    /**
     * Fade out.
     */
    public val fadeOut: SceneTransition = object : SceneTransition {
        override fun toString(): String {
            return "fade out"
        }
    }

    /**
     * Fade in.
     */
    public val fadeIn: SceneTransition = object : SceneTransition {
        override fun toString(): String {
            return "fade in"
        }
    }

    /**
     * Cross fade.
     */
    public val crossFade: SceneTransition = object : SceneTransition {
        override fun toString(): String {
            return "cross fade"
        }
    }
}
