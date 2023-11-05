package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.example.ExampleScript
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {
        val sceneListener = object : SceneListener {
            override fun enter(scene: Scene) {
                println("Entered ${scene.type}")
            }

            override fun exit(scene: Scene) {
                println("Exited ${scene.type}")
            }
        }

        val chapterListener = object : ChapterListener {
            override fun enter(chapter: Chapter) {
                println("Entered ${chapter.name}")
            }

            override fun exit(chapter: Chapter) {
                println("Exited ${chapter.name}")
            }
        }

        logger.info("Beginning execution of example...")
        val example = ExampleScript(ConsoleListenerProvider)
        example.begin(chapterListener, sceneListener)
        logger.info("Ended execution of example.")
    }
}
