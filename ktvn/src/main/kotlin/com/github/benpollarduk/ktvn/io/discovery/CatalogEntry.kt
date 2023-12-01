package com.github.benpollarduk.ktvn.io.discovery

/**
 * Provides an entry in a catalog with a friendly [name] and a fully qualified class name.
 */
public data class CatalogEntry<T>(
    public val name: String,
    public val qualifiedClassName: String,
    public val entry: T
)
