package com.github.benpollarduk.ktvn.io

/**
 * Provides the result of a load operation.
 */
public data class FileLoadResult<T>(
    public val result: Boolean,
    public val message: String,
    public val obj: T
)
