package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult

/**
 * A step that acts an end. A [setup] must be specified.
 */
public class End private constructor(private val setup: (End) -> Unit) : Step {
    private var script: (Flags) -> Unit = { }
        private set

    private var ending: Int = 0
        private set

    override var name: String = ""
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

    /**
     * Set the ending.
     */
    public infix fun number(ending: Int) {
        this.ending = ending
    }

    override fun invoke(flags: Flags): StepResult {
        script(flags)
        return StepResult.End(ending)
    }

    public companion object {
        /**
         * Create a step with a specified [setup].
         */
        public infix fun end(setup: (End) -> Unit): End {
            return End(setup)
        }
    }
}
