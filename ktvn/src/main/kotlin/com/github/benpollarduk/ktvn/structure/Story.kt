package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.io.restore.StoryRestorePoint
import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags

/**
 * A story. A [setup] must be specified.
 */
public class Story private constructor(setup: Story.() -> Unit) {
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
    public var flags: Flags = Flags.EMPTY
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
     * Get all chapters in this story.
     */
    internal fun getAllChapters(): List<Chapter> {
        return chapters.toList()
    }

    /**
     * Begin the [Story] with specified [parameters]. Returns an [Ending].
     **/
    internal fun begin(parameters: StoryBeginParameters): Ending {
        indexOfCurrentChapter = maxOf(0, minOf(chapters.size - 1, parameters.storyRestorePoint.chapter - 1))
        var ending: Ending? = null
        parameters.storyAdapter.storyListener.enter(this)

        while (indexOfCurrentChapter < chapters.size) {
            val chapter = chapters[indexOfCurrentChapter]

            val result = chapter.begin(
                ChapterBeginParameters(
                    flags,
                    parameters.storyRestorePoint.chapterRestorePoint,
                    parameters.stepTracker,
                    parameters.cancellationToken
                ),
                parameters.storyAdapter.chapterListener,
                parameters.storyAdapter.sceneListener,
                parameters.storyAdapter.stepListener
            )

            when (result) {
                is ChapterResult.Continue -> { indexOfCurrentChapter++ }
                is ChapterResult.SelectChapter -> {
                    indexOfCurrentChapter = chapters.indexOfFirst { it.name.equals(result.name, true) }
                }
                is ChapterResult.End -> { ending = result.ending }
                is ChapterResult.Cancelled -> { ending = Ending.none }
            }

            if (ending != null) {
                break
            }
        }

        parameters.storyAdapter.storyListener.exit(this)

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
        public infix fun story(setup: Story.() -> Unit): Story {
            return Story(setup)
        }
    }
}
