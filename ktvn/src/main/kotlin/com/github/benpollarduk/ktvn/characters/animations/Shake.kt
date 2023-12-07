package com.github.benpollarduk.ktvn.characters.animations

/**
 * Provides an animation which has a shaking effect with a specified [strengthX], [strengthY] and number of
 * [oscillations]. The [strengthX] and [strengthY] should be specified within the normalised range of 0-1.
 * [NO_OSCILLATION_LIMIT] can be specified for [oscillations] if the animation should continue indefinitely. The
 * [framesPerSecond] specifies how many frames of the animation are displayed per second.
 */
public class Shake(
    public val strengthX: Double,
    public val strengthY: Double,
    public val oscillations: Int,
    public val framesPerSecond: Int
) : Animation {
    override fun toString(): String {
        return "shake"
    }

    public companion object {
        /**
         * A value representing no oscillation limit.
         */
        public const val NO_OSCILLATION_LIMIT: Int = -1
    }
}
