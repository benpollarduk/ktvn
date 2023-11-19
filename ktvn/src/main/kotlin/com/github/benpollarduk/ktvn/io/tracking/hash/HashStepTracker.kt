package com.github.benpollarduk.ktvn.io.tracking.hash

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.structure.Step

/**
 * Provides a simple implementation of a [StepTracker] that uses hash codes to track if a [Step] has been seen.
 * Optionally the [hashTable] can be provided.
 */
public class HashStepTracker
@JsonCreator
constructor(
    @JsonProperty("hashTable")
    private var initialHashTable: MutableList<Int> = mutableListOf()
) : StepTracker {
    /**
     * Get the version of this [HashStepTracker].
     */
    public val version: String = VERSION_1_0_0

    /**
     * Get the underlying hash table.
     */
    public val hashTable: List<Int>
        get() = initialHashTable.toList()

    private fun hasBeenSeen(hash: Int): Boolean {
        return initialHashTable.contains(hash)
    }

    override fun hasBeenSeen(step: Step): Boolean {
        val hash = step.hashCode()
        return hasBeenSeen(hash)
    }

    override fun registerStepSeen(step: Step) {
        val hash = step.hashCode()
        if (!hasBeenSeen(hash)) {
            initialHashTable.add(hash)
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
