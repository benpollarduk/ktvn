package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult

/**
 * A step that clears a scene. A [setup] must be specified.
 */
public class Clear private constructor(private val setup: (Clear) -> Unit) : Step {
    override var name: String = "Clear"
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

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        return if (cancellationToken.wasCancelled) {
            StepResult.Cancelled
        } else {
            StepResult.Clear
        }
    }

    public companion object {
        /**
         * Create a step with a specified [setup].
         */
        public infix fun clear(setup: (Clear) -> Unit): Clear {
            return Clear(setup)
        }
    }
}
