package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.io.restore.ChapterRestorePoint
import com.github.benpollarduk.ktvn.io.restore.StoryRestorePoint
import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags

/**
 * A story. A [setup] must be specified.
 */
public class Story private constructor(setup: (Story) -> Unit) {
    private val chapters: MutableList<Chapter> = mutableListOf()

    /**
     * Get the name of this [Chapter].
     */
    public var name: String = "Story"
        private set

    /**
     * Get the number of the [Chapter] in this [Story].
     */
    public val numberOfChapters: Int
        get() = chapters.size

    /**
     * Get the flags
     */
    public var flags: Flags = Flags.empty
        private set

    /**
     * Get the index of the current [Chapter].
     */
    internal var indexOfCurrentChapter: Int = 0
        private set

    /**
     * Get the current [Chapter].
     */
    internal val currentChapter: Chapter
        get() = chapters[indexOfCurrentChapter]

    init {
        setup(this)
    }

    /**
     * Create a restore point for this [Story]. The returned [StoryRestorePoint] allows this [Story] to be restored.
     */
    internal fun createRestorePoint(): StoryRestorePoint {
        return StoryRestorePoint(currentChapter.createRestorePoint(), indexOfCurrentChapter)
    }

    /**
     * Begin the [Story] with specified [flags] and [parameters]. Returns an [Ending].
     **/
    internal fun begin(
        flags: Flags,
        parameters: StoryBeginParameters
    ): Ending {
        var i = parameters.storyRestorePoint.chapter
        var ending: Ending? = null

        while (i < chapters.size) {
            indexOfCurrentChapter = i
            val chapter = chapters[i]

            val result = if (i == parameters.storyRestorePoint.chapter) {
                chapter.begin(
                    flags,
                    ChapterBeginParameters(
                        parameters.storyRestorePoint.chapterRestorePoint,
                        parameters.storyConfiguration.sceneListener,
                        parameters.storyConfiguration.chapterListener,
                        parameters.stepTracker,
                        parameters.progressionMode,
                        parameters.cancellationToken
                    )
                )
            } else {
                chapter.begin(
                    flags,
                    ChapterBeginParameters(
                        ChapterRestorePoint.start,
                        parameters.storyConfiguration.sceneListener,
                        parameters.storyConfiguration.chapterListener,
                        parameters.stepTracker,
                        parameters.progressionMode,
                        parameters.cancellationToken
                    )
                )
            }

            when (result) {
                is ChapterResult.Continue -> { i++ }
                is ChapterResult.SelectChapter -> { i = chapters.indexOfFirst { it.name.equals(result.name, true) } }
                is ChapterResult.End -> { ending = result.ending }
                is ChapterResult.Cancelled -> { ending = Ending.noEnding }
            }

            if (ending != null) {
                break
            }
        }

        return ending ?: Ending.default
    }

    /**
     * Set the [name] of this [Story].
     */
    public infix fun name(name: String) {
        this.name = name
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
