package com.github.benpollarduk.ktvn.io.discovery

/**
 * Provides a catalog of elements of <T>.
 */
public class Catalog<T>(private var elements: List<CatalogEntry<T>>) {
    /**
     * Get all elements in this catalog.
     */
    public fun get(): List<CatalogEntry<T>> {
        return elements
    }

    /**
     * Merge another [Catalog] in to this one.
     */
    public fun merge(catalog: Catalog<T>) {
        elements = elements.plus(catalog.get())
    }
}
