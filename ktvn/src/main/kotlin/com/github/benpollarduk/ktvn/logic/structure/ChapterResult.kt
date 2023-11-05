package com.github.benpollarduk.ktvn.logic.structure

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
    public data class End(public val ending: Int) : ChapterResult
}
