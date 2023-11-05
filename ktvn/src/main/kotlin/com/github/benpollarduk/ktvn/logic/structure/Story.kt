package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Flags

/**
 * A story. A [setup] must be specified.
 */
public class Story private constructor(setup: (Story) -> Unit) {
    private val chapters: MutableList<Chapter> = mutableListOf()

    /**
     * Get the index of the current [Chapter].
     */
    public var indexOfCurrentChapter: Int = 0
        private set

    /**
     * Get the flags
     */
    public var flags: Flags = Flags.empty
        private set

    /**
     * Get the current position within this story.
     */
    public val currentPosition: StoryPosition
        get() {
            val scene = chapters[indexOfCurrentChapter].currentScene
            val step = scene.indexOfCurrentStep
            return StoryPosition(indexOfCurrentChapter, chapters[indexOfCurrentChapter].indexOfCurrentScene, step)
        }

    init {
        setup(this)
    }

    /**
     * Begin the [Story] with specified [flags]. The [storyPosition] can be optionally specified. Returns the ending
     * number reached.
     **/
    @Suppress("ReturnCount")
    public fun begin(
        flags: Flags,
        storyPosition: StoryPosition = StoryPosition.start,
        chapterListener: ChapterListener,
        sceneListener: SceneListener
    ): Int {
        var i = storyPosition.chapter

        while (i < chapters.size) {
            indexOfCurrentChapter = i
            val chapter = chapters[i]
            chapterListener.enter(chapter)

            val result = if (i == storyPosition.chapter) {
                chapter.begin(flags, storyPosition.scene, storyPosition.step, sceneListener)
            } else {
                chapter.begin(flags, sceneListener = sceneListener)
            }

            chapterListener.exit(chapter)

            when (result) {
                is ChapterResult.Continue -> { i++ }
                is ChapterResult.SelectChapter -> { i = chapters.indexOfFirst { it.name.equals(result.name, true) } }
                is ChapterResult.End -> { return result.ending }
            }
        }

        return 0
    }

    /**
     * Add a [chapter] to this [Story].
     */
    public infix fun add(chapter: Chapter) {
        chapters.add(chapter)
    }

    public companion object {
        /**
         * Create a new [Story] with a specified [setup].
         */
        public infix fun story(setup: (Story) -> Unit): Story {
            return Story(setup)
        }
    }
}
