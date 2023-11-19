package com.github.benpollarduk.ktvn.io.game

import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
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
    public fun toFile(gameSave: GameSave, path: String): SaveResult {
        return try {
            val contents = GameSaveJsonParser.toJson(gameSave)
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
     * Return a [GameSave] from a specified [path].
     */
    public fun fromFile(path: String): LoadResult<GameSave> {
        return try {
            // if future versions of GameSave are releases the version will need to be located and the JSON passed to a
            // suitable parser. as there is only 1 version at present this is not needed
            val json = File(path).readText()
            LoadResult(true, "", GameSaveJsonParser.fromJson(json))
        } catch (e: IOException) {
            LoadResult(false, e.message ?: "Load failed - IOException.", GameSave.empty)
        } catch (e: FileNotFoundException) {
            LoadResult(false, e.message ?: "Load failed - FileNotFoundException.", GameSave.empty)
        } catch (e: SecurityException) {
            LoadResult(false, e.message ?: "Load failed - SecurityException.", GameSave.empty)
        }
    }
}
