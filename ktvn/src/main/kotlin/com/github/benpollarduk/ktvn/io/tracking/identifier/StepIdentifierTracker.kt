package com.github.benpollarduk.ktvn.io.tracking.identifier

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepIdentifier

/**
 * Provides a simple implementation of a [StepTracker] that uses [StepIdentifier] to track if a [Step] has been seen.
 * Optionally the [table] can be provided. This provides a simple mechanism for tracking steps and may not be
 * suitable for games with greater than 100,000 steps as the lookup will become more costly and serialization
 * less suitable.
 */
public class StepIdentifierTracker
@JsonCreator
constructor(
    @JsonProperty("table")
    private var initialTable: MutableList<String> = mutableListOf()
) : StepTracker {
    /**
     * Get the version of this [StepIdentifierTracker].
     */
    public val version: String = VERSION_1_0_0

    /**
     * Get the underlying table.
     */
    public val table: List<String>
        get() = initialTable.toList()

    private fun hasBeenSeen(identifier: String): Boolean {
        return initialTable.contains(identifier)
    }

    override fun hasBeenSeen(step: Step): Boolean {
        val identifier = step.identifier.toString()
        return hasBeenSeen(identifier)
    }

    override fun registerStepSeen(step: Step) {
        val identifier = step.identifier.toString()
        if (!hasBeenSeen(identifier)) {
            initialTable.add(identifier)
        }
    }

    override fun restore(path: String): LoadResult<StepTracker> {
        val result = StepIdentifierTrackerSerializer.fromFile(path)
        if (result.result) {
            this.initialTable = result.loadedObject.initialTable
        }
        return LoadResult(result.result, result.message, result.loadedObject as StepTracker)
    }

    override fun persist(path: String): SaveResult {
        return StepIdentifierTrackerSerializer.toFile(this, path)
    }

    public companion object {
        /**
         * Get the version string for version 1.0.
         */
        public const val VERSION_1_0_0: String = "1.0.0"

        /**
         * Provides an empty [StepIdentifierTracker].
         */
        public val EMPTY: StepIdentifierTracker = StepIdentifierTracker()
    }
}
