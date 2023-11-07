package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.Ending

/**
 * Provides the result of a [Step].
 */
public sealed interface StepResult {
    /**
     * Continue to next [Step].
     */
    public data object Continue : StepResult

    /**
     * Select a [Chapter] specified by [name].
     */
    public data class SelectChapter(public val name: String) : StepResult

    /**
     * Select a [Scene] specified by [name].
     */
    public data class SelectScene(public val name: String) : StepResult

    /**
     * Go to a [Step] specified by [name].
     */
    public data class GotoStep(public val name: String) : StepResult

    /**
     * The specified [ending] has been reached.
     */
    public data class End(public val ending: Ending) : StepResult

    /**
     * Execution was cancelled.
     */
    public data object Cancelled : StepResult
}
