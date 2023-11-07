package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Ending

/**
 * Provides the result of a [Chapter].
 */
public sealed interface ChapterResult {
    /**
     * Continue to next [Chapter].
     */
    public data object Continue : ChapterResult

    /**
     * Select a [Chapter] specified by [name].
     */
    public data class SelectChapter(public val name: String) : ChapterResult

    /**
     * The specified [ending] has been reached.
     */
    public data class End(public val ending: Ending) : ChapterResult

    /**
     * Execution was cancelled.
     */
    public data object Cancelled : ChapterResult
}
