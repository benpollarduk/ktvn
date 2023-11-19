package com.github.benpollarduk.ktvn.io.game

import com.github.benpollarduk.ktvn.io.FileLoadResult
import com.github.benpollarduk.ktvn.io.FileSaveResult
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Provides a serializer for [GameSave] objects.
 */
public object GameSaveSerializer {
    /**
     * Save a [gameSave] to a specified [path].
     */
    public fun toFile(gameSave: GameSave, path: String): FileSaveResult {
        return try {
            val contents = GameSaveJsonParser.toJson(gameSave)
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
     * Return a [GameSave] from a specified [path].
     */
    public fun fromFile(path: String): FileLoadResult<GameSave> {
        return try {
            // if future versions of GameSave are releases the version will need to be located and the JSON passed to a
            // suitable parser. as there is only 1 version at present this is not needed
            val json = File(path).readText()
            FileLoadResult(true, "", GameSaveJsonParser.fromJson(json))
        } catch (e: IOException) {
            FileLoadResult(false, e.message ?: "Load failed - IOException.", GameSave.empty)
        } catch (e: FileNotFoundException) {
            FileLoadResult(false, e.message ?: "Load failed - FileNotFoundException.", GameSave.empty)
        } catch (e: SecurityException) {
            FileLoadResult(false, e.message ?: "Load failed - SecurityException.", GameSave.empty)
        }
    }
}
