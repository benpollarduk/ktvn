package com.github.benpollarduk.ktvn.characters.animations

/**
 * Provides an animation which has a shaking effect with a specified [strength] and number of [oscillations]. The
 * [strength] should be specified within the normalised range of 0-1. [NO_OSCILLATION_LIMIT] can be specified for
 * [oscillations] if the animation should continue indefinitely. The [framesPerSecond] specifies how many frames of the
 * animation are displayed per second.
 */
public class Laugh(
    public val strength: Double,
    public val oscillations: Int,
    public val framesPerSecond: Int
) : Animation {
    override fun toString(): String {
        return "laugh"
    }

    public companion object {
        /**
         * A value representing no oscillation limit.
         */
        public const val NO_OSCILLATION_LIMIT: Int = -1
    }
}
