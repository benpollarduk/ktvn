package com.github.benpollarduk.ktvn.io.game

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Provides an object for parsing a [GameSave] to and from JSON.
 */
public object GameSaveJsonParser {
    /**
     * Parse a [GameSave] from a [json] string.
     */
    public fun fromJson(json: String): GameSave {
        return jacksonObjectMapper().readValue<GameSave>(json)
    }

    /**
     * Parse a [GameSave] to a JSON string.
     */
    public fun toJson(gameSave: GameSave): String {
        return jacksonObjectMapper().writeValueAsString(gameSave)
    }
}
