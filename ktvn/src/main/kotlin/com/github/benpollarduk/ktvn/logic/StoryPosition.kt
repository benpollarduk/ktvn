package com.github.benpollarduk.ktvn.logic

/**
 * Provides a position in a [Story], with a specified [act], [scene] and [step].
 */
public class StoryPosition(
    public val act: Int,
    public val scene: Int,
    public val step: Int
) {
    override fun toString(): String {
        return "$act.$scene.$step"
    }

    public companion object {
        /**
         * Provides the position at the start of a [Story].
         */
        public val start: StoryPosition = StoryPosition(0, 0, 0)
    }
}
