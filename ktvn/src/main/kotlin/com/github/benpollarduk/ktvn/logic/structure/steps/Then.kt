package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult

/**
 * A step that acts as a progression. A [setup] must be specified.
 */
public class Then private constructor(private val setup: (Then) -> Unit) : Step {
    private var script: (Flags) -> Unit = { }
        private set

    override var name: String = "Then"
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
    public infix fun does(script: (Flags) -> Unit) {
        this.script = script
    }

    override fun invoke(flags: Flags): StepResult {
        script(flags)
        return StepResult.Continue
    }

    public companion object {
        /**
         * Create a step with a specified [setup].
         */
        public infix fun then(setup: (Then) -> Unit): Then {
            return Then(setup)
        }

        /**
         * Create a step with a specified [script]. This is a simple wrapper of 'then' that provides the same
         * functionality but with simplified syntax.
         */
        public infix fun next(script: (Flags) -> Unit): Then {
            return Then {
                it does script
            }
        }
    }
}
