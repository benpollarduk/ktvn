package com.github.benpollarduk.ktvn.io

/**
 * Provides the result of a save operation.
 */
public data class SaveResult(
    public val result: Boolean,
    public val message: String
)
