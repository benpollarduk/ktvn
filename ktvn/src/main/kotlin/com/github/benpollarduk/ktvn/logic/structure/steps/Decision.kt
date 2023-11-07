package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult

/**
 * A step that acts as a decision. A [setup] must be specified.
 */
public class Decision private constructor(private val setup: (Decision) -> Unit) : Step {
    private var script: (Flags) -> Answer = { answer { } }

    override var name: String = "Decision"
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
     * Set the script.
     */
    public infix fun does(script: (Flags) -> Answer) {
        this.script = script
    }

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        val answer = script(flags)

        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        answer.script(flags)

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
        public infix fun decision(setup: (Decision) -> Unit): Decision {
            return Decision(setup)
        }

        /**
         * Create a step with a specified [script]. This is a simple wrapper of 'decision' that provides the same
         * functionality but with simplified syntax.
         */
        public infix fun choice(script: (Flags) -> Answer): Decision {
            return Decision {
                it does script
            }
        }
    }
}
