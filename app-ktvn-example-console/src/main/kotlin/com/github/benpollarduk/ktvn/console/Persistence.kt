package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.game.GameSaveSerializer
import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTracker
import com.github.benpollarduk.ktvn.io.tracking.hash.HashStepTrackerSerializer
import java.nio.file.FileSystems
import kotlin.io.path.absolutePathString
import org.apache.logging.log4j.kotlin.Logging

/**
 * Provides persistence functionality for the game.
 */
internal object Persistence : Logging {
    /**
     * Get the file system.
     */
    private val fileSystem = FileSystems.getDefault()

    /**
     * Get the game save path.
     */
    private val gameSavePath = fileSystem.getPath(
        System.getProperty("user.dir"),
        "data",
        "game.save"
    ).absolutePathString()

    /**
     * Get the step path.
     */
    private val stepDataPath = fileSystem.getPath(
        System.getProperty("user.dir"),
        "data",
        "step.save"
    ).absolutePathString()

    /**
     * Restore a [GameSave] from a specified [path].
     */
    internal fun restoreGameSave(path: String = gameSavePath) : GameSave {
        val result = GameSaveSerializer.fromFile(path)
        return if (result.result) {
            logger.info("Got game save from $path")
            result.loadedObject
        } else {
            logger.debug("Couldn't get game save from $path because ${result.message}")
            GameSave.empty
        }
    }

    /**
     * Persist a [gameSave] to a specified [path].
     */
    internal fun persistGameSave(gameSave: GameSave, path: String = gameSavePath) {
        val result = GameSaveSerializer.toFile(gameSave, path)
        return if (result.result) {
            logger.info("Saved game save to $path")
        } else {
            logger.debug("Couldn't save game save to $path because ${result.message}")
        }
    }

    /**
     * Persist step data to the specified [path].
     */
    internal fun persistStepData(path: String = stepDataPath) : SaveResult {
        return HashStepTrackerSerializer.toFile(configuration.stepTracker as HashStepTracker, path)
    }

    /**
     * Restore step data from the specified [path].
     */
    internal fun restoreStepData(path: String = stepDataPath) : LoadResult<HashStepTracker> {
        val result = HashStepTrackerSerializer.fromFile(path)
        if (result.result) {
            configuration.stepTracker = result.loadedObject
        }
        return result
    }
}