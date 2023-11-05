package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.example.ExampleCreator
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.listeners.console.ConsoleListenerProvider
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.StoryPosition
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
        }

        val chapterListener = object : ChapterListener {
            override fun enter(chapter: Chapter) {
                ConsoleListenerProvider.clear()
                println("Started chapter: ${chapter.name}")
            }

            override fun exit(chapter: Chapter) {
                println("Ended chapter: ${chapter.name}")
            }
        }

        logger.info("Beginning execution of example...")
        val example = ExampleCreator(ConsoleListenerProvider).create()
        example.begin(Flags(), StoryPosition.start, chapterListener, sceneListener)
        logger.info("Ended execution of example.")
    }
}
