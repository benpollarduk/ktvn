package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.StepIdentifier
import com.github.benpollarduk.ktvn.structure.StepResult

/**
 * A step that signifies an end. A [setup] must be specified.
 */
public class End private constructor(private val setup: End.() -> Unit) : Step {
    private var script: (Flags) -> Unit = { }
    private var ending: Ending = Ending.default

    override var name: String = "End"
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
        public infix fun end(setup: End.() -> Unit): End {
            return End(setup)
        }
    }
}
