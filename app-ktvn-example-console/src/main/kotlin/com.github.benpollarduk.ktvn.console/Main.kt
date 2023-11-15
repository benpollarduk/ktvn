package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.console.configuration.AnsiConsoleController
import com.github.benpollarduk.ktvn.console.configuration.AnsiConsoleGameConfiguration
import com.github.benpollarduk.ktvn.example.ExampleGame
import com.github.benpollarduk.ktvn.logic.GameExecutor
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("Beginning execution of example...")

        // create the controller responsible for handling all input and output
        val controller = AnsiConsoleController()

        // create the console configuration for interacting with the game
        val ansiConsoleGameConfiguration = AnsiConsoleGameConfiguration(controller)

        // create an example game
        val exampleGame = ExampleGame(ansiConsoleGameConfiguration).create()

        // execute the game on its own thread
        GameExecutor.executeAysnc(exampleGame) {
            // the game has finished so input no long needs to be processed
            controller.endProcessingInput()
        }

        // allow the console controller to process input from the console.
        // this will block the thread until consoleController.endProcessingInput is called
        controller.beginProcessingInput()

        logger.info("Ended execution of example.")
    }
}
