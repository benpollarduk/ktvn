package com.github.benpollarduk.ktvn.io.tracking.identifier

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Provides an object for parsing a [StepIdentifierTracker] to and from JSON.
 */
public object StepIdentifierTrackerJsonParser {
    /**
     * Parse a [StepIdentifierTracker] from a [json] string.
     */
    public fun fromJson(json: String): StepIdentifierTracker {
        return jacksonObjectMapper().readValue<StepIdentifierTracker>(json)
    }

    /**
     * Parse a [StepIdentifierTracker] to a JSON string.
     */
    public fun toJson(stepIdentifierTracker: StepIdentifierTracker): String {
        return jacksonObjectMapper().writeValueAsString(stepIdentifierTracker)
    }
}
