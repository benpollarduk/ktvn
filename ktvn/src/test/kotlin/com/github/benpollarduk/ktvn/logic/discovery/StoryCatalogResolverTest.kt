package com.github.benpollarduk.ktvn.logic.discovery

import com.github.benpollarduk.ktvn.io.discovery.StoryCatalogResolver
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream

@Suppress("MaxLineLength")
class StoryCatalogResolverTest {
    @Test
    fun `given example jar containing one story template object when resolving catalog entries for jar file then one entry returned`() {
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
        val result = StoryCatalogResolver.resolveCatalogFromJar(tempFile)

        // Then
        Assertions.assertEquals(1, result.get().size)
        Assertions.assertEquals("ktvn demo", result.get().first().name)
    }
}
