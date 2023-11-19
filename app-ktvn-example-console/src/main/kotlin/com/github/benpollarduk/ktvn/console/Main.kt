package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.Persistence.persistGameSave
import com.github.benpollarduk.ktvn.console.Persistence.persistStepData
import com.github.benpollarduk.ktvn.console.Persistence.restoreGameSave
import com.github.benpollarduk.ktvn.console.Persistence.restoreStepData
import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.controller
import com.github.benpollarduk.ktvn.console.story.theFateOfMorgana
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("Beginning execution of example...")

        // restore previous game save
        val gameSave = restoreGameSave()

        // restore previous step data
        restoreStepData()

        // create an example game
        val exampleGame = Game(theFateOfMorgana(), configuration, gameSave, RestorePoint.empty)

        // execute the game on its own thread
        GameExecutor.executeAysnc(exampleGame) {
            // the game has finished so input no long needs to be processed
            controller.endProcessingInput()

            // persist the game save
            persistGameSave(it.gameSave)

            // persist the step data
            persistStepData()
        }

        // allow the console controller to process input from the console.
        // this will block the thread until consoleGameController.endProcessingInput is called
        controller.beginProcessingInput()

        logger.info("Ended execution of example.")
    }
}
