package com.github.benpollarduk.ktvn.logic.adapters.dynamic

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.GameEngine
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepListener
import com.github.benpollarduk.ktvn.logic.structure.Story
import com.github.benpollarduk.ktvn.logic.structure.StoryListener

/**
 * Provides a [StoryAdapter] with a [gameEngine] that can be specified after initialization.
 */
internal class DynamicStoryAdapter(internal var gameEngine: GameEngine? = null) : StoryAdapter {
    override val storyListener: StoryListener = object : StoryListener {
        override fun enter(story: Story) {
            gameEngine?.enterStory(story)
        }

        override fun exit(story: Story) {
            gameEngine?.exitStory(story)
        }
    }

    override val chapterListener: ChapterListener = object : ChapterListener {
        override fun enter(chapter: Chapter, transition: ChapterTransition) {
            gameEngine?.enterChapter(chapter, transition)
        }

        override fun exit(chapter: Chapter) {
            gameEngine?.exitChapter(chapter)
        }
    }

    override val sceneListener: SceneListener = object : SceneListener {
        override fun enter(scene: Scene, transition: SceneTransition) {
            gameEngine?.enterScene(scene, transition)
        }

        override fun exit(scene: Scene, transition: SceneTransition) {
            gameEngine?.exitScene(scene, transition)
        }

        override fun clear(scene: Scene) {
            gameEngine?.clearScene(scene)
        }
    }

    override val stepListener: StepListener = object : StepListener {
        override fun enter(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken) {
            gameEngine?.enterStep(step, flags, canSkip, cancellationToken)
        }

        override fun exit(step: Step, flags: Flags) {
            gameEngine?.exitStep(step, flags)
        }
    }
}