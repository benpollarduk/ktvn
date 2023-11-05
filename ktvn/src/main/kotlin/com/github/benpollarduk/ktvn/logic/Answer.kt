package com.github.benpollarduk.ktvn.logic

/**
 * An answer to a question.
 */
public class Answer private constructor(setup: (Answer) -> Unit) {
    /**
     * Get the line.
     */
    public var line: String = ""
        private set

    /**
     * Get the script.
     */
    public var script: (Flags) -> Unit = {}
        private set

    init {
        setup(this)
    }

    /**
     * Set the line.
     */
    public infix fun line(line: String) {
        this.line = line
    }

    /**
     * Specify the script for when this answer is selected.
     */
    public infix fun does(script: (Flags) -> Unit) {
        this.script = script
    }

    public companion object {
        /**
         * Create an [Answer] with a specified [callback].
         */
        public infix fun answer(setup: (Answer) -> Unit): Answer {
            return Answer(setup)
        }
    }
}
