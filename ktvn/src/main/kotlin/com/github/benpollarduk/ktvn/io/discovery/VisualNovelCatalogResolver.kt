package com.github.benpollarduk.ktvn.io.discovery

import com.github.benpollarduk.ktvn.logic.VisualNovel
import java.io.File
import java.lang.reflect.Modifier
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile

/**
 * Provides an object for resolving a [Catalog] of [VisualNovel] objects.
 */
public object VisualNovelCatalogResolver {
    /**
     * Get a list of all class names from a [jarFile].
     */
    private fun getClassNamesFromJar(jarFile: File): List<String> {
        val classNames = mutableListOf<String>()
        val jar = JarFile(jarFile)

        val entries = jar.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.name.endsWith(".class")) {
                val className = entry.name
                    .replace("/", ".")
                    .removeSuffix(".class")
                classNames.add(className)
            }
        }

        return classNames
    }

    /**
     * Get a URL for a specified [jarFile].
     */
    public fun getURL(jarFile: File): URL {
        return jarFile.toURI().toURL()
    }

    /**
     * Resolve a [Catalog] from a jar file.
     */
    public fun resolveCatalogFromJar(jarFile: File): Catalog<VisualNovel> {
        val url = getURL(jarFile)
        val classLoader = URLClassLoader(arrayOf(url))
        return resolveCatalogFromJar(jarFile, classLoader)
    }

    /**
     * Resolve a [Catalog] from a jar file with a specified [classLoader].
     */
    public fun resolveCatalogFromJar(jarFile: File, classLoader: URLClassLoader): Catalog<VisualNovel> {
        val jarClassLoader = URLClassLoader(arrayOf(jarFile.toURI().toURL()), classLoader)
        val jarClassNames = getClassNamesFromJar(jarFile)
        val catalog = mutableListOf<CatalogEntry<VisualNovel>>()

        // iterate all class names in jar
        for (className in jarClassNames) {
            val loadedClass = Class.forName(className, true, jarClassLoader)

            // determine if the class is a subclass or object of VisualNovel
            if (VisualNovel::class.java.isAssignableFrom(loadedClass) &&
                !loadedClass.isInterface &&
                !Modifier.isAbstract(loadedClass.modifiers)
            ) {
                // check if an object
                val visualNovel = if (loadedClass.kotlin.objectInstance != null) {
                    loadedClass.kotlin.objectInstance as VisualNovel
                } else {
                    // dealing with subclass
                    val constructor = loadedClass.getDeclaredConstructor()
                    constructor.newInstance() as VisualNovel
                }
                // add new catalog entry for the story
                val story = visualNovel.story
                catalog.add(CatalogEntry(story.name, className, visualNovel))
            }
        }

        return Catalog(catalog)
    }

    /**
     * Resolve a [Catalog] from a [path] to a directory. As default a relative path to '/stories' will be used.
     */
    public fun resolveCatalogFromDirectory(path: String): Catalog<VisualNovel> {
        val catalogs = mutableListOf<Catalog<VisualNovel>>()
        val directory = File(path)

        if (!directory.isDirectory) {
            return Catalog(emptyList())
        }

        // locate all jar files in the specified path
        val jarFiles = directory.listFiles { file ->
            file.isFile && file.extension.equals("jar", true)
        }

        // get all URLs for jar files and create class loader
        val urls = jarFiles.map { getURL(it) }.toTypedArray()
        val classLoader = URLClassLoader(urls)

        jarFiles.forEach {
            catalogs.add(resolveCatalogFromJar(it, classLoader))
        }

        return Catalog(catalogs.toList().flatMap { it.get() })
    }
}
