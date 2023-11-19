package com.github.benpollarduk.ktvn.io.tracking.hash

import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Provides a serializer for [HashStepTracker] objects.
 */
public object HashStepTrackerSerializer {
    /**
     * Save a [hashStepTracker] to a specified [path].
     */
    public fun toFile(hashStepTracker: HashStepTracker, path: String): SaveResult {
        return try {
            val contents = HashStepTrackerJsonParser.toJson(hashStepTracker)
            File(path).writeText(contents)
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
     * Return a [HashStepTracker] from a specified [path].
     */
    public fun fromFile(path: String): LoadResult<HashStepTracker> {
        return try {
            // if future versions of HashStepTracker are releases the version will need to be located and the JSON
            // passed to a suitable parser. as there is only 1 version at present this is not needed
            val json = File(path).readText()
            LoadResult(true, "", HashStepTrackerJsonParser.fromJson(json))
        } catch (e: IOException) {
            LoadResult(false, e.message ?: "Load failed - IOException.", HashStepTracker.empty)
        } catch (e: FileNotFoundException) {
            LoadResult(false, e.message ?: "Load failed - FileNotFoundException.", HashStepTracker.empty)
        } catch (e: SecurityException) {
            LoadResult(false, e.message ?: "Load failed - SecurityException.", HashStepTracker.empty)
        }
    }
}
