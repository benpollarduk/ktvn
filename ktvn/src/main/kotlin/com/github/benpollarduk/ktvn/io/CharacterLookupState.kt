package com.github.benpollarduk.ktvn.io

/**
 * Captures the state a [characterName], [characterVariation] and [emotion] for lookup purposes.
 */
public data class CharacterLookupState(
    public val characterName: String,
    public val characterVariation: String,
    public val emotion: String
)
