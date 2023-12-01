package com.github.benpollarduk.ktvn.io.discovery

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream

@Suppress("MaxLineLength")
class VisualNovelCatalogResolverTest {
    @Test
    fun `given example jar containing one story template object when resolving catalog entries for jar file then 2 entries returned`() {
        // Given
        val testJarStream = javaClass.classLoader.getResourceAsStream("test.jar")
        val tempFile = File.createTempFile("test-jar", ".jar")
        tempFile.deleteOnExit()

        // write the contents of the resource to the temporary file
        testJarStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }

        // When
        val result = VisualNovelCatalogResolver.resolveCatalogFromJar(tempFile)

        // Then
        Assertions.assertEquals(2, result.get().size)
    }
}
