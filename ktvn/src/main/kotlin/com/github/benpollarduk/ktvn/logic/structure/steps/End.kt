package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult

/**
 * A step that acts an end. A [setup] must be specified.
 */
public class End private constructor(private val setup: (End) -> Unit) : Step {
    private var script: (Flags) -> Unit = { }
    private var ending: Ending = Ending.default

    override var name: String = "End"
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
    public infix fun ending(ending: Ending) {
        this.ending = ending
    }

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        script(flags)

        return if (cancellationToken.wasCancelled) {
            StepResult.Cancelled
        } else {
            StepResult.End(ending)
        }
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