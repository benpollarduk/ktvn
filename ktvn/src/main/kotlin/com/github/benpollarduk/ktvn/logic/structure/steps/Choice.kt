package com.github.benpollarduk.ktvn.logic.structure.steps

import com.github.benpollarduk.ktvn.logic.Answer
import com.github.benpollarduk.ktvn.logic.Answer.Companion.answer
import com.github.benpollarduk.ktvn.logic.Flags
import com.github.benpollarduk.ktvn.logic.structure.Step
import com.github.benpollarduk.ktvn.logic.structure.StepResult

/**
 * A step that acts as a choice. A [setup] must be specified.
 */
public class Choice private constructor(private val setup: (Choice) -> Unit) : Step {
    private var script: (Flags) -> Answer = { answer { } }
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
    public infix fun does(script: (Flags) -> Answer) {
        this.script = script
    }

    override fun invoke(flags: Flags): StepResult {
        val answer = script(flags)
        answer.script(flags)
        return StepResult.Continue
    }

    public companion object {
        /**
         * Create a choice with a specified [setup].
         */
        public infix fun choice(setup: (Choice) -> Unit): Choice {
            return Choice(setup)
        }
    }
}
