package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an identifier for a step consisting of [chapter], [scene] and [step].
 */
public data class StepIdentifier(
    public val chapter: Int,
    public val scene: Int,
    public val step: Int
) {
    override fun toString(): String {
        return "$chapter-$scene-$step"
    }

    public companion object {
        /**
         * Get a default value for no identifier.
         */
        public val NONE: StepIdentifier = StepIdentifier(-1, -1, -1)
    }
}
