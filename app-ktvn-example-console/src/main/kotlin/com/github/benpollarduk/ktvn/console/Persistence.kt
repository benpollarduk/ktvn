package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.game.GameSaveSerializer
import com.github.benpollarduk.ktvn.io.tracking.StepTracker
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
    internal val stepDataPath = fileSystem.getPath(
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
            logger.error("Couldn't restore game save from $path because ${result.message}")
            GameSave.empty
        }
    }

    /**
     * Persist a [gameSave] to a specified [path].
     */
    internal fun persistGameSave(gameSave: GameSave, path: String = gameSavePath) {
        val result = GameSaveSerializer.toFile(gameSave, path)
        if (result.result) {
            logger.info("Persisted game save to $path")
        } else {
            logger.error("Couldn't persist game save to $path because ${result.message}")
        }
    }

    /**
     * Restore previously saved step data from a [path].
     */
    internal fun restoreStepData(stepTracker: StepTracker, path: String = stepDataPath) {
        val result = stepTracker.restore(path)
        if (result.result) {
            logger.info("Restored step data from $path")
        } else {
            logger.error("Couldn't restore step data from $path because ${result.message}")
        }
    }

    /**
     * Persist step data to a specified [path].
     */
    internal fun persistStepData(stepTracker: StepTracker, path: String = stepDataPath) {
        val result = stepTracker.persist(path)
        if (result.result) {
            logger.info("Persisted step data to $path")
        } else {
            logger.debug("Couldn't persist step data to $path because ${result.message}")
        }
    }
}