package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.story.assets.AssetStore.configuration
import com.github.benpollarduk.ktvn.console.story.theFateOfMorgana
import com.github.benpollarduk.ktvn.io.game.GameSave
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("Beginning execution of example...")

        // create an example game
        val exampleGame = Game(theFateOfMorgana(), configuration, GameSave.empty, RestorePoint.empty)

        // execute the game on its own thread
        GameExecutor.executeAysnc(exampleGame) {
            // the game has finished so input no long needs to be processed
            configuration.consoleController.endProcessingInput()
        }

        // allow the console controller to process input from the console.
        // this will block the thread until consoleController.endProcessingInput is called
        configuration.consoleController.beginProcessingInput()

        logger.info("Ended execution of example.")
    }
}
