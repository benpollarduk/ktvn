package com.github.benpollarduk.ktvn.console.configuration

import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepListener

/**
 * Provides an [StoryConfiguration] for an ANSI console.
 */
internal class AnsiConsoleStoryConfiguration(
    private val consoleController: AnsiConsoleController
) : StoryConfiguration {
    override val chapterListener: ChapterListener = object : ChapterListener {
        override fun enter(chapter: Chapter, transition: ChapterTransition) {
            consoleController.clear()
            consoleController.printlnDirectTemp("Started chapter '${chapter.name}' with transition $transition.")
        }

        override fun exit(chapter: Chapter) {
            consoleController.printlnDirectTemp("Ended chapter '${chapter.name}'.")
        }
    }

    override val sceneListener: SceneListener = object : SceneListener {
        override fun enter(scene: Scene, transition: SceneTransition) {
            consoleController.printlnDirectTemp("Entered scene '${scene.name}' with transition $transition.")
        }

        override fun exit(scene: Scene, transition: SceneTransition) {
            consoleController.printlnDirectTemp("Exited scene '${scene.name}' with transition $transition.")
        }

        override fun clear(scene: Scene) {
            consoleController.clear()
        }
    }

    override val stepListener: StepListener = object : StepListener {
        override fun enter(step: Step) {
            // nothing
        }

        override fun exit(step: Step) {
            // nothing
        }
    }
}
