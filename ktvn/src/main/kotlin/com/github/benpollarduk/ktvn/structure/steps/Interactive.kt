package com.github.benpollarduk.ktvn.structure.steps

import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.InteractiveComponent
import com.github.benpollarduk.ktvn.structure.CancellationToken
import com.github.benpollarduk.ktvn.structure.Step
import com.github.benpollarduk.ktvn.structure.StepIdentifier
import com.github.benpollarduk.ktvn.structure.StepResult

/**
 * A step that contains an interactive. A [setup] must be specified.
 */
public class Interactive private constructor(private val setup: Interactive.() -> Unit) : Step {
    private var interactiveComponent: InteractiveComponent? = null
    private var args: Array<String> = emptyArray()

    override var name: String = "interactive"
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
     * Set the [InteractiveComponent] element.
     */
    public infix fun element(interactiveComponent: InteractiveComponent) {
        this.interactiveComponent = interactiveComponent
    }

    /**
     * Set the args to pass when invoking the [InteractiveComponent].
     */
    public infix fun args(args: Array<String>) {
        this.args = args
    }

    override fun invoke(flags: Flags, cancellationToken: CancellationToken): StepResult {
        if (cancellationToken.wasCancelled) {
            return StepResult.Cancelled
        }

        val result = interactiveComponent?.invoke(args, flags, cancellationToken) ?: StepResult.Continue

        return if (cancellationToken.wasCancelled) {
            StepResult.Cancelled
        } else {
            result
        }
    }

    public companion object {
        /**
         * Create a step with a specified [setup].
         */
        public infix fun interactive(setup: Interactive.() -> Unit): Interactive {
            return Interactive(setup)
        }
    }
}
