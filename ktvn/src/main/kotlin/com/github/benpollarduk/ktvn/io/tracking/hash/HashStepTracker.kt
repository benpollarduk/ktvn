package com.github.benpollarduk.ktvn.io.tracking.hash

import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.structure.Step

/**
 * Provides a simple implementation of a [StepTracker] that uses hash codes to track if a [Step] has been seen.
 * Optionally the [hashList] can be provided.
 */
public class HashStepTracker(public var hashList: MutableList<Int> = mutableListOf()) : StepTracker {
    /**
     * Get the version of this [HashStepTracker].
     */
    public val version: String = VERSION_1_0_0

    private fun hasBeenSeen(hash: Int): Boolean {
        return hashList.contains(hash)
    }

    override fun hasBeenSeen(step: Step): Boolean {
        val hash = step.hashCode()
        return hasBeenSeen(hash)
    }

    override fun registerStepSeen(step: Step) {
        val hash = step.hashCode()
        if (!hasBeenSeen(hash)) {
            hashList.add(hash)
        }
    }

    public companion object {
        /**
         * Get the version string for version 1.0.
         */
        public const val VERSION_1_0_0: String = "1.0.0"

        /**
         * Provides an empty [HashStepTracker].
         */
        public val empty: HashStepTracker = HashStepTracker()
    }
}
