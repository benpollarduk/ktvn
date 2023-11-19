package com.github.benpollarduk.ktvn.logic.configuration.default

import com.github.benpollarduk.ktvn.logic.GameController
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
 * Provides a default [StoryConfiguration] with a specified [gameController].
 */
internal class DefaultStoryConfiguration(private val gameController: GameController) : StoryConfiguration {
    override val chapterListener: ChapterListener = object : ChapterListener {
        override fun enter(chapter: Chapter, transition: ChapterTransition) {
            gameController.enterChapter(chapter, transition)
        }

        override fun exit(chapter: Chapter) {
            gameController.exitChapter(chapter)
        }
    }

    override val sceneListener: SceneListener = object : SceneListener {
        override fun enter(scene: Scene, transition: SceneTransition) {
            gameController.enterScene(scene, transition)
        }

        override fun exit(scene: Scene, transition: SceneTransition) {
            gameController.exitScene(scene, transition)
        }

        override fun clear(scene: Scene) {
            gameController.clearScene(scene)
        }
    }

    override val stepListener: StepListener = object : StepListener {
        override fun enter(step: Step, canSkip: Boolean) {
            gameController.enterStep(step, canSkip)
        }

        override fun exit(step: Step) {
            gameController.exitStep(step)
        }
    }
}
