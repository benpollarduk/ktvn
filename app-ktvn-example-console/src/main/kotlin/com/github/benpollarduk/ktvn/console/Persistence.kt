package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.io.LoadResult
import com.github.benpollarduk.ktvn.io.SaveResult
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.game.GameSaveSerializer
import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTracker
import com.github.benpollarduk.ktvn.io.tracking.identifier.StepIdentifierTrackerSerializer
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
            logger.info("Restored game save from $path")
            result.loadedObject
        } else {
            logger.debug("Couldn't restore game save from $path because ${result.message}")
            GameSave.empty
        }
    }

    /**
     * Persist a [gameSave] to a specified [path].
     */
    internal fun persistGameSave(gameSave: GameSave, path: String = gameSavePath) {
        val result = GameSaveSerializer.toFile(gameSave, path)
        return if (result.result) {
            logger.info("Persisted game save to $path")
        } else {
            logger.debug("Couldn't persist game save to $path because ${result.message}")
        }
    }

    /**
     * Persist step data to the specified [path].
     */
    internal fun persistStepData(path: String = stepDataPath) : SaveResult {
        return StepIdentifierTrackerSerializer.toFile(configuration.stepTracker as StepIdentifierTracker, path)
    }

    /**
     * Restore step data from the specified [path].
     */
    internal fun restoreStepData(path: String = stepDataPath) : LoadResult<StepIdentifierTracker> {
        val result = StepIdentifierTrackerSerializer.fromFile(path)
        if (result.result) {
            configuration.stepTracker = result.loadedObject
        }
        return result
    }
}