package com.github.benpollarduk.ktvn.io.tracking.identifier

import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Provides a serializer for [StepIdentifierTracker] objects.
 */
public object StepIdentifierTrackerSerializer {
    /**
     * Save a [stepIdentifierTracker] to a specified [path].
     */
    public fun toFile(stepIdentifierTracker: StepIdentifierTracker, path: String): SaveResult {
        return try {
            val contents = StepIdentifierTrackerJsonParser.toJson(stepIdentifierTracker)
            val file = File(path)
            file.parentFile.mkdirs()
            file.writeText(contents)
            SaveResult(true, "")
        } catch (e: IOException) {
            SaveResult(false, e.message ?: "Save failed - IOException.")
        } catch (e: FileNotFoundException) {
            SaveResult(false, e.message ?: "Save failed - FileNotFoundException.")
        } catch (e: SecurityException) {
            SaveResult(false, e.message ?: "Save failed - SecurityException.")
        }
    }

    /**
     * Return a [StepIdentifierTracker] from a specified [path].
     */
    public fun fromFile(path: String): LoadResult<StepIdentifierTracker> {
        return try {
            // if future versions of StepIdentifierTracker are releases the version will need to be located and the
            // JSON passed to a suitable parser. as there is only 1 version at present this is not needed
            val json = File(path).readText()
            LoadResult(true, "", StepIdentifierTrackerJsonParser.fromJson(json))
        } catch (e: IOException) {
            LoadResult(false, e.message ?: "Load failed - IOException.", StepIdentifierTracker.EMPTY)
        } catch (e: FileNotFoundException) {
            LoadResult(false, e.message ?: "Load failed - FileNotFoundException.", StepIdentifierTracker.EMPTY)
        } catch (e: SecurityException) {
            LoadResult(false, e.message ?: "Load failed - SecurityException.", StepIdentifierTracker.EMPTY)
        }
    }
}
