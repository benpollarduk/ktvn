package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult

/**
 * A step that only happens if a condition is true. A [script] must be specified.
 */
public class Conditional private constructor(setup: (Conditional) -> Unit) : Step {
    private var script: (Flags) -> Unit = { }
        private set

    private var flag: String = ""
    private var result: StepResult = StepResult.Continue
        private set

    override var name: String = "Conditional"
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
     * Set the flag that is checked as part of the condition.
     */
    public infix fun condition(flag: String) {
        this.flag = flag
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

    override fun invoke(flags: Flags): StepResult {
        if (flags[flag]) {
            script(flags)
            return result
        }

        return StepResult.Continue
    }

    public companion object {
        /**
         * Create a step with a specified [setup].
         */
        public infix fun conditional(setup: (Conditional) -> Unit): Conditional {
            return Conditional(setup)
        }
    }
}
