package com.github.benpollarduk.ktvn.io.restore

import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Provides a serializer for [RestorePoint] objects.
 */
public object RestorePointSerializer {
    /**
     * Save a [restorePoint] to a specified [path].
     */
    public fun toFile(restorePoint: RestorePoint, path: String): SaveResult {
        return try {
            val contents = RestorePointJsonParser.toJson(restorePoint)
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
     * Return a [RestorePoint] from a specified [path].
     */
    public fun fromFile(path: String): LoadResult<RestorePoint> {
        return try {
            // if future versions of RestorePoint are releases the version will need to be located and the JSON passed
            // to a suitable parser. as there is only 1 version at present this is not needed
            val json = File(path).readText()
            LoadResult(true, "", RestorePointJsonParser.fromJson(json))
        } catch (e: IOException) {
            LoadResult(false, e.message ?: "Load failed - IOException.", RestorePoint.empty)
        } catch (e: FileNotFoundException) {
            LoadResult(false, e.message ?: "Load failed - FileNotFoundException.", RestorePoint.empty)
        } catch (e: SecurityException) {
            LoadResult(false, e.message ?: "Load failed - SecurityException.", RestorePoint.empty)
        }
    }
}
