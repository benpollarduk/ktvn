package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.StepIdentifier
import com.github.benpollarduk.ktvn.structure.StepResult

/**
 * A step that clears a scene. A [setup] must be specified.
 */
public class Clear private constructor(private val setup: (Clear) -> Unit) : Step {
    override var name: String = "Clear"
        private set

    override var identifier: StepIdentifier = StepIdentifier(0, 0, 0)

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
