package com.github.benpollarduk.ktvn.io

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Provides an object for parsing a [Save] to and from JSON.
 */
public object SaveJsonParser {
    /**
     * Return a [Save] from a [json] string.
     */
    public fun fromJson(json: String): Save {
        return jacksonObjectMapper().readValue<Save>(json)
    }

    /**
     * Convert a [save] to a JSON string.
     */
    public fun toJson(save: Save): String {
        return jacksonObjectMapper().writeValueAsString(save)
    }
}
