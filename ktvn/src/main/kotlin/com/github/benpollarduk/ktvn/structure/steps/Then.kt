package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.StepIdentifier
import com.github.benpollarduk.ktvn.structure.StepResult

/**
 * A step that acts as a progression. A [setup] must be specified.
 */
public class Then private constructor(private val setup: Then.() -> Unit) : Step {
    private var script: (Flags) -> Unit = { }

    override var name: String = "Then"
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

    /**
     * Set the script.
     */
    public infix fun does(script: (Flags) -> Unit) {
        this.script = script
    }

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        script(flags)

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
        public infix fun then(setup: Then.() -> Unit): Then {
            return Then(setup)
        }

        /**
         * Create a step with a specified [script]. This is a simple wrapper of 'then' that provides the same
         * functionality but with simplified syntax.
         */
        public infix fun next(script: (Flags) -> Unit): Then {
            return Then {
                this does script
            }
        }
    }
}
