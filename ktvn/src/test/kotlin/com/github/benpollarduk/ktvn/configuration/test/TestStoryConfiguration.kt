package com.github.benpollarduk.ktvn.configuration.test

import com.github.benpollarduk.ktvn.logic.configuration.StoryConfiguration
import com.github.benpollarduk.ktvn.logic.structure.Chapter
import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.ChapterTransition
import com.github.benpollarduk.ktvn.logic.structure.Scene
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.SceneTransition

internal class TestStoryConfiguration : StoryConfiguration {
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

    override val chapterListener: ChapterListener = object : ChapterListener {
        override fun enter(chapter: Chapter, transition: ChapterTransition) {
            // nothing
        }

        override fun exit(chapter: Chapter) {
            // nothing
        }
    }
}
