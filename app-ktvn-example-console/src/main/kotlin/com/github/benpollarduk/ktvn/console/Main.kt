package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.console.story.theFateOfMorgana
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.game.GameSaveSerializer
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    /**
     * Restore a [GameSave] from a specified [path].
     */
    private fun restoreGameSave(path: String) : GameSave {
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
    private fun persistGameSave(gameSave: GameSave, path: String) {
        val result = GameSaveSerializer.toFile(gameSave, path)
        return if (result.result) {
            logger.info("Saved game save to $path")
        } else {
            logger.debug("Couldn't save game save to $path because ${result.message}")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("Beginning execution of example...")

        // create the game save path
        val gameSavePath = "${System.getProperty("user.dir")}data/game.save"

        // create the step data path
        val stepDataPath = "${System.getProperty("user.dir")}data/step.save"

        // restore previous game save
        val gameSave = restoreGameSave(gameSavePath)

        // restore previous step data
        configuration.restoreStepData(stepDataPath)

        // create an example game
        val exampleGame = Game(theFateOfMorgana(), configuration, gameSave, RestorePoint.empty)

        // execute the game on its own thread
        GameExecutor.executeAysnc(exampleGame) {
            // the game has finished so input no long needs to be processed
            configuration.consoleController.endProcessingInput()

            // persist the game save
            persistGameSave(gameSave, gameSavePath)

            // persist the step data
            configuration.persistStepData(stepDataPath)
        }

        // allow the console controller to process input from the console.
        // this will block the thread until consoleController.endProcessingInput is called
        configuration.consoleController.beginProcessingInput()

        logger.info("Ended execution of example.")
    }
}
