package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.StepIdentifier
import com.github.benpollarduk.ktvn.structure.StepResult

/**
 * A step that only happens if a condition is true. A [script] must be specified.
 */
public class Conditional private constructor(setup: Conditional.() -> Unit) : Step {
    private var script: (Flags) -> Unit = { }
    private var flag: String = ""
    private var result: StepResult = StepResult.Continue
    private var state: Boolean = true

    override var name: String = "Conditional"
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
     * Set the flag that is checked as part of the condition.
     */
    public infix fun condition(flag: String) {
        this.flag = flag
    }

    /**
     * Set the desired state of the flag that is checked as part of the condition.
     */
    public infix fun state(state: Boolean) {
        this.state = state
    }

    /**
     * Set the script that is invoked if the condition is true.
     */
    public infix fun does(script: (Flags) -> Unit) {
        this.script = script
    }

    /**
     * Set the [result] that is returned when the condition is met.
     */
    public infix fun returns(result: StepResult) {
        this.result = result
    }

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        return if (flags[flag] == state) {
            script(flags)
            result
        } else {
            StepResult.Continue
        }
    }

    public companion object {
        /**
         * Create a step with a specified [setup].
         */
        public infix fun conditional(setup: Conditional.() -> Unit): Conditional {
            return Conditional(setup)
        }
    }
}
