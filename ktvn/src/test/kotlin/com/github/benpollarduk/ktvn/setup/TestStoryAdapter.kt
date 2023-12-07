package com.github.benpollarduk.ktvn.setup

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.adapters.StoryAdapter
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Chapter
import com.github.benpollarduk.ktvn.structure.ChapterListener
import com.github.benpollarduk.ktvn.structure.ChapterTransition
import com.github.benpollarduk.ktvn.structure.Scene
import com.github.benpollarduk.ktvn.structure.SceneListener
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.StepListener
import com.github.benpollarduk.ktvn.structure.Story
import com.github.benpollarduk.ktvn.structure.StoryListener
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition

internal class TestStoryAdapter : StoryAdapter {
    override val storyListener: StoryListener = object : StoryListener {
        override fun enter(story: Story) {
            // nothing
        }

        override fun exit(story: Story) {
            // nothing
        }
    }

    override val chapterListener: ChapterListener = object : ChapterListener {
        override fun enter(chapter: Chapter, transition: ChapterTransition) {
            // nothing
        }

        override fun exit(chapter: Chapter) {
            // nothing
        }
    }

    override val sceneListener: SceneListener = object : SceneListener {
        override fun enter(scene: Scene, transition: SceneTransition) {
            // nothing
        }

        override fun exit(scene: Scene, transition: SceneTransition) {
            // nothing
        }

        override fun clear(scene: Scene) {
            // nothing
        }
    }

    override val stepListener: StepListener = object : StepListener {
        override fun enter(step: Step, flags: Flags, canSkip: Boolean, cancellationToken: CancellationToken) {
            // nothing
        }

        override fun exit(step: Step, flags: Flags) {
            // nothing
        }
    }
}
