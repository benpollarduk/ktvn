package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.example.ExampleCreator
import com.github.benpollarduk.ktvn.io.Save
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import com.github.benpollarduk.ktvn.logic.configuration.console.AnsiConsoleGameConfiguration
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {


        logger.info("Beginning execution of example...")
        val exampleStory = ExampleCreator(AnsiConsoleGameConfiguration).create()
        val exampleGame = Game(exampleStory, AnsiConsoleGameConfiguration.storyConfiguration, Save.empty)
        GameExecutor.execute(exampleGame)
        logger.info("Ended execution of example.")
    }
}
