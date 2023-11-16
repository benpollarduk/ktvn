package com.github.benpollarduk.ktvn.io

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

public object SaveSerializer {
    /**
     * Save a [save] to a specified [path].
     */
    public fun toFile(save: Save, path: String): FileSaveResult {
        return try {
            val contents = SaveJsonParser.toJson(save)
            File(path).writeText(contents)
            FileSaveResult(true, "")
        } catch (e: IOException) {
            FileSaveResult(false, e.message ?: "Save failed - IOException.")
        } catch (e: FileNotFoundException) {
            FileSaveResult(false, e.message ?: "Save failed - FileNotFoundException.")
        } catch (e: SecurityException) {
            FileSaveResult(false, e.message ?: "Save failed - SecurityException.")
        }
    }

    /**
     * Return a [Save] from a specified [path].
     */
    public fun fromFile(path: String): FileLoadResult {
        return try {
            // if future versions of Save are releases the version will need to be located and the JSON passed to a
            // suitable parser. as there is only 1 version at present this is not needed
            val json = File(path).readText()
            FileLoadResult(true, "", SaveJsonParser.fromJson(json))
        } catch (e: IOException) {
            FileLoadResult(false, e.message ?: "Load failed - IOException.", Save.empty)
        } catch (e: FileNotFoundException) {
            FileLoadResult(false, e.message ?: "Load failed - FileNotFoundException.", Save.empty)
        } catch (e: SecurityException) {
            FileLoadResult(false, e.message ?: "Load failed - SecurityException.", Save.empty)
        }
    }
}
