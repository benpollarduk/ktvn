package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.example.ExampleCreator
import com.github.benpollarduk.ktvn.io.Save
import com.github.benpollarduk.ktvn.logic.Game
import com.github.benpollarduk.ktvn.logic.GameExecutor
import com.github.benpollarduk.ktvn.logic.configuration.console.AnsiConsoleInteractionConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.listeners.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.listeners.SceneListener
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {
        val sceneListener = object : SceneListener {
            override fun enter(scene: Scene) {
                println("Entered scene: ${scene.name}")
            }

            override fun exit(scene: Scene) {
                println("Exited scene: ${scene.name}")
            }

            override fun clear(scene: Scene) {
                AnsiConsoleInteractionConfiguration.clearConsole()
            }
        }

        val chapterListener = object : ChapterListener {
            override fun enter(chapter: Chapter) {
                AnsiConsoleInteractionConfiguration.clearConsole()
                println("Started chapter: ${chapter.name}")
            }

            override fun exit(chapter: Chapter) {
                println("Ended chapter: ${chapter.name}")
            }
        }

        logger.info("Beginning execution of example...")
        val exampleStory = ExampleCreator(AnsiConsoleInteractionConfiguration).create()
        val exampleGame = Game(exampleStory, Save.empty, chapterListener, sceneListener)
        GameExecutor.execute(exampleGame)
        logger.info("Ended execution of example.")
    }
}
