package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult
import kotlin.math.min

/**
 * A step that provides a pause. A [setup] must be specified.
 */
public class Pause private constructor(private val setup: (Pause) -> Unit) : Step {
    private var delayInMs: Long = 0

    override var name: String = "Pause"
        private set

    init {
        setup(this)
    }

    /**
     * Set the [name] of this step.
     */
    public infix fun name(name: String) {
        this.name = name
    }

    /**
     * Set the delay (in milliseconds).
     */
    public infix fun milliseconds(delayInMs: Long) {
        this.delayInMs = delayInMs
    }

    /**
     * Set the delay (in seconds).
     */
    public infix fun seconds(delayInS: Long) {
        this.delayInMs = delayInS * 1000
    }

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        val startTime = System.currentTimeMillis()
        var currentTime = startTime

        while (currentTime - startTime < delayInMs && !cancellationToken.wasCancelled) {
            val remaining = delayInMs - (currentTime - startTime)
            val delay = min(remaining, DELAY_BETWEEN_CHECKS_IN_MS)

            if (delay > 0) {
                Thread.sleep(delay)
                currentTime = System.currentTimeMillis()
            }
        }

        return if (cancellationToken.wasCancelled) {
            StepResult.Cancelled
        } else {
            StepResult.Continue
        }
    }

    public companion object {
        /**
         * Create a step with a specified [setup].
         */
        public infix fun pause(setup: (Pause) -> Unit): Pause {
            return Pause(setup)
        }

        private const val DELAY_BETWEEN_CHECKS_IN_MS: Long = 10
    }
}
