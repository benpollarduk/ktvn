package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.configuration.AnsiConsoleGameConfiguration
import com.github.benpollarduk.ktvn.example.ExampleCreator
import com.github.benpollarduk.ktvn.io.Save
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("Beginning execution of example...")

        // create an example story, and then inject in to a game
        val exampleStory = ExampleCreator(AnsiConsoleGameConfiguration).create()
        val exampleGame = Game(exampleStory, AnsiConsoleGameConfiguration, Save.empty)

        // execute the game on its own thread
        GameExecutor.executeAysnc(exampleGame) {
            // the game has finished so input no long needs to be processed
            AnsiConsoleGameConfiguration.consoleController.endProcessingInput()
        }

        // allow the console controller to process input from the console.
        // this will block the thread until consoleController.endProcessingInput is called
        AnsiConsoleGameConfiguration.consoleController.beginProcessingInput()

        logger.info("Ended execution of example.")
    }
}
