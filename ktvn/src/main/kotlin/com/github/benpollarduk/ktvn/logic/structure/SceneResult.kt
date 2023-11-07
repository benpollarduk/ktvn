package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Ending

/**
 * Provides the result of a [Scene].
 */
public sealed interface SceneResult {
    /**
     * Continue to next [Scene].
     */
    public data object Continue : SceneResult

    /**
     * Select a [Chapter] specified by [name].
     */
    public data class SelectChapter(public val name: String) : SceneResult

    /**
     * Select a [Scene] specified by [name].
     */
    public data class SelectScene(public val name: String) : SceneResult

    /**
     * The specified [ending] has been reached.
     */
    public data class End(public val ending: Ending) : SceneResult

    /**
     * Execution was cancelled.
     */
    public data object Cancelled : SceneResult
}
