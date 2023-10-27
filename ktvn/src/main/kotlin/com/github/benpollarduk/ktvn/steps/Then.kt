package com.github.benpollarduk.ktvn.steps

public class Then private constructor(private val script: () -> Unit) : Step {
    override fun invoke() {
        script()
    }

    public companion object {
        public infix fun then(callback: () -> Unit): Then {
            return Then(callback)
        }
    }
}
