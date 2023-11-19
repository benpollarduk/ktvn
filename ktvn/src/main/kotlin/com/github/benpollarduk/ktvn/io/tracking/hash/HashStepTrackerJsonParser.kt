package com.github.benpollarduk.ktvn.io.tracking.hash

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Provides an object for parsing a [HashStepTracker] to and from JSON.
 */
public object HashStepTrackerJsonParser {
    /**
     * Parse a [HashStepTracker] from a [json] string.
     */
    public fun fromJson(json: String): HashStepTracker {
        return jacksonObjectMapper().readValue<HashStepTracker>(json)
    }

    /**
     * Parse a [HashStepTracker] to a JSON string.
     */
    public fun toJson(hashStepTracker: HashStepTracker): String {
        return jacksonObjectMapper().writeValueAsString(hashStepTracker)
    }
}
