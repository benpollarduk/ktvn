package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.Persistence.persistGameSave
import com.github.benpollarduk.ktvn.console.Persistence.restoreGameSave
import com.github.benpollarduk.ktvn.example.TheFateOfMorgana
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    /**
     * The engine responsible for handling all input and output.
     */
    private val engine = AnsiConsoleGameEngine()

    /**
     * The story template.
     */
    private val storyTemplate = TheFateOfMorgana().also {
        it.configuration.engine = engine
    }

    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("Beginning execution of example...")

        // restore previous game save
        val gameSave = restoreGameSave()

        // restore step tracking data
        Persistence.restoreStepData(storyTemplate.configuration.stepTracker)

        // create an example game
        val exampleGame = Game(storyTemplate, gameSave, RestorePoint.empty)

        // execute the game on its own thread
        GameExecutor.executeAysnc(exampleGame) {
            // the game has finished so input no long needs to be processed
            engine.endProcessingInput()

            // persist the game save
            persistGameSave(it.gameSave)

            // persist the step data
            Persistence.persistStepData(storyTemplate.configuration.stepTracker)
        }

        // allow the console engine to process input from the console.
        // this will block the thread until consoleGameController.endProcessingInput is called
        engine.beginProcessingInput()

        logger.info("Ended execution of example.")
    }
}
