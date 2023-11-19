package com.github.benpollarduk.ktvn.io.restore

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Provides an object for parsing a [RestorePoint] to and from JSON.
 */
public object RestorePointJsonParser {
    /**
     * Parse a [RestorePoint] from a [json] string.
     */
    public fun fromJson(json: String): RestorePoint {
        return jacksonObjectMapper().readValue<RestorePoint>(json)
    }

    /**
     * Parse a [restorePoint] to a JSON string.
     */
    public fun toJson(restorePoint: RestorePoint): String {
        return jacksonObjectMapper().writeValueAsString(restorePoint)
    }
}
