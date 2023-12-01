package com.github.benpollarduk.ktvn.prototyper.console

import com.github.benpollarduk.ktvn.examples.theFateOfMorgana.TheFateOfMorgana
import com.github.benpollarduk.ktvn.prototyper.console.Persistence.persistGameSave
import com.github.benpollarduk.ktvn.prototyper.console.Persistence.restoreGameSave
import com.github.benpollarduk.ktvn.io.discovery.CatalogEntry
import com.github.benpollarduk.ktvn.io.discovery.VisualNovelCatalogResolver
import com.github.benpollarduk.ktvn.io.restore.RestorePoint
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import com.github.benpollarduk.ktvn.logic.VisualNovel
import java.io.File
import java.lang.NumberFormatException
import org.apache.logging.log4j.kotlin.Logging

@Suppress("TooGenericException")
object Main : Logging {
    /**
     * The engine responsible for handling all input and output.
     */
    private val engine = AnsiConsoleGameEngine()

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            // get visual novel
            val visualNovel = handleVisualNovelSelection(args.firstOrNull() ?: "")

            // assign engine to configuration
            visualNovel.configuration.engine = engine

            logger.info("Beginning execution of visual novel...")

            // restore previous game save
            val gameSave = restoreGameSave()

            // restore step tracking data
            Persistence.restoreStepData(visualNovel.configuration.stepTracker)

            // create an example game
            val exampleGame = Game(visualNovel, gameSave, RestorePoint.EMPTY)

            // execute the game on its own thread
            GameExecutor.executeAysnc(exampleGame) {
                // the game has finished so input no long needs to be processed
                engine.endProcessingInput()

                // persist the game save
                persistGameSave(it.gameSave)

                // persist the step data
                Persistence.persistStepData(visualNovel.configuration.stepTracker)
            }

            // allow the console engine to process input from the console.
            // this will block the thread until consoleGameController.endProcessingInput is called
            engine.beginProcessingInput()

            logger.info("Ended execution of visual novel.")
        } catch (e: Exception) {
            logger.error("Exception caught in execution of visual novel: ${e.message}")
            println("Exception caught in execution of visual novel: ${e.message}")
            readln()
        }
    }


    private fun handleInvalidPath(path: String): VisualNovel {
        logger.error("Invalid path: $path.")
        println("Invalid path. Only .jar files are compatible.")
        println("Example will be used.")
        readln()
        return TheFateOfMorgana()
    }

    private fun handleNoTemplateInJar(path: String): VisualNovel {
        logger.error("No Visual novels were found in $path.")
        println("No visual novels found. Visual novels must be subclasses of VisualNovel.")
        println("Example will be used.")
        readln()
        return TheFateOfMorgana()
    }

    private fun handleMultipleVisualNovelsInJar(novels: List<CatalogEntry<VisualNovel>>): VisualNovel {
        println("Found multiple visual novels, select a number to load that visual novel:")
        for (i in novels.indices) {
            println("${i + 1}: ${novels[i].name}")
        }

        while (true) {
            val selection = readln()
            try {
                val selectedIndex = selection.toInt()
                val correctedIndex = selectedIndex - 1
                if (correctedIndex >= 0 && correctedIndex < novels.size) {
                    return novels[correctedIndex].entry
                }
            }
            catch (e: NumberFormatException) {
                // just loop
            }
        }
    }

    private fun handleVisualNovelSelection(path: String): VisualNovel {
        return if (path.isNotEmpty()) {
            // can only be a .jar file
            if (!path.endsWith(".jar", true)) {
                handleInvalidPath(path)
            } else {
                // load visual novels from file
                val catalog = VisualNovelCatalogResolver.resolveCatalogFromJar(File(path))
                val templates = catalog.get()
                when (templates.size) {
                    0 -> handleNoTemplateInJar(path)
                    1 -> templates.first().entry
                    else -> handleMultipleVisualNovelsInJar(templates)
                }
            }
        } else {
            TheFateOfMorgana()
        }
    }
}
