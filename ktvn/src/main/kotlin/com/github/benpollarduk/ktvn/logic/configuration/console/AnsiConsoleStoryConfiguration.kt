package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition

/**
 * Provides an [StoryConfiguration] for an ANSI console.
 */
internal class AnsiConsoleStoryConfiguration : StoryConfiguration {
    override val sceneListener: SceneListener = object : SceneListener {
        override fun enter(scene: Scene, transition: SceneTransition) {
            println("Entered scene ${scene.name} with transition $transition.")
        }

        override fun exit(scene: Scene, transition: SceneTransition) {
            println("Exited scene ${scene.name} with transition $transition.")
        }

        override fun clear(scene: Scene) {
            AnsiConsoleGameConfiguration.clearConsole()
        }
    }

    override val chapterListener: ChapterListener = object : ChapterListener {
        override fun enter(chapter: Chapter, transition: ChapterTransition) {
            AnsiConsoleGameConfiguration.clearConsole()
            println("Started chapter ${chapter.name} with transition $transition.")
        }

        override fun exit(chapter: Chapter) {
            println("Ended chapter ${chapter.name}.")
        }
    }
}
