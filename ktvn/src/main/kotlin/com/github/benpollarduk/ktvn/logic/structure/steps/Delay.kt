package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult
import kotlin.math.min

/**
 * A step that acts an end. A [setup] must be specified.
 */
public class Delay private constructor(private val setup: (Delay) -> Unit) : Step {
    private var delayInMs: Long = 0

    override var name: String = "Delay"
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
    public infix fun time(delayInMs: Long) {
        this.delayInMs = delayInMs
    }

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        val startTime = System.currentTimeMillis()

        while (System.currentTimeMillis() - startTime < delayInMs && !cancellationToken.wasCancelled) {
            Thread.sleep(min(delayInMs, RECHECK_TIME_IN_MS))
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
        public infix fun delay(setup: (Delay) -> Unit): Delay {
            return Delay(setup)
        }

        private const val RECHECK_TIME_IN_MS: Long = 10
    }
}
