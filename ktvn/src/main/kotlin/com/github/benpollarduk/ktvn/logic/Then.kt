package com.github.benpollarduk.ktvn.logic

/**
 * Provides a default 'next' step.
 */
public class Then private constructor(private val script: () -> Unit) : Step {
    override fun invoke(flags: Flags) {
        script()
    }

    public companion object {
        /**
         * Create a 'next' step with a specified [callback].
         */
        public infix fun then(callback: () -> Unit): Then {
            return Then(callback)
        }
    }
}
