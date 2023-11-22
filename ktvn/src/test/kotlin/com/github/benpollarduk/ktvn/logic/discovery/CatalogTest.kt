package com.github.benpollarduk.ktvn.logic.discovery

import com.github.benpollarduk.ktvn.io.discovery.Catalog
import com.github.benpollarduk.ktvn.io.discovery.CatalogEntry
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CatalogTest {
    @Test
    fun `given catalog with 5 elements when getting then return 5 elements`() {
        // Given
        val elements = listOf(
            CatalogEntry("", "", 1),
            CatalogEntry("", "", 2),
            CatalogEntry("", "", 3),
            CatalogEntry("", "", 4),
            CatalogEntry("", "", 5)
        )
        val catalog = Catalog(elements)

        // When
        val result = catalog.get().size

        // Then
        Assertions.assertEquals(5, result)
    }

    @Test
    fun `given catalog with 2 elements and catalog with 1 element when merge then return 3 elements`() {
        // Given
        val elements1 = listOf(
            CatalogEntry("", "", 1),
            CatalogEntry("", "", 2)
        )
        val catalog1 = Catalog(elements1)
        val elements2 = listOf(
            CatalogEntry("", "", 1)
        )
        val catalog2 = Catalog(elements2)

        // When
        catalog1.merge(catalog2)
        val result = catalog1.get().size

        // Then
        Assertions.assertEquals(3, result)
    }
}
