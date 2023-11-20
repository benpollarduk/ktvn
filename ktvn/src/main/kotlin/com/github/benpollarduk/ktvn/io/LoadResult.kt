package com.github.benpollarduk.ktvn.io

/**
 * Provides the result of a load operation.
 */
public data class LoadResult<T>(
    public val result: Boolean,
    public val message: String,
    public val loadedObject: T
)
