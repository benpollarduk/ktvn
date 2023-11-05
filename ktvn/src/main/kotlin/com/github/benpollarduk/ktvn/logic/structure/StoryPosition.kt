package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides a position in a [Story], with a specified [chapter], [scene] and [step].
 */
public class StoryPosition(
    public val chapter: Int,
    public val scene: Int,
    public val step: Int
) {
    override fun toString(): String {
        return "$chapter.$scene.$step"
    }

    public companion object {
        /**
         * Provides the position at the start of a [Story].
         */
        public val start: StoryPosition = StoryPosition(0, 0, 0)
    }
}
