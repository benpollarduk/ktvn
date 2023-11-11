package com.github.benpollarduk.ktvn.logic

/**
 * A question, provides the option for a user to branch a story based on input.
 */
public class Question private constructor(setup: (Question) -> Unit) {
    private val _answers: MutableList<Answer> = mutableListOf()

    /**
     * Get the line.
     */
    public var line: String = ""
        private set

    /**
     * Get the available answerListener.
     */
    public val answers: List<Answer>
        get() = _answers.toList()

    init {
        setup(this)
    }

    /**
     * Specify the [line].
     */
    public infix fun line(line: String) {
        this.line = line
    }

    /**
     * Add the specified list of [answers].
     */
    public infix fun options(options: List<Answer>) {
        this._answers.addAll(options)
    }

    /**
     * Add an [answer].
     */
    public infix fun option(answer: Answer) {
        _answers.add(answer)
    }

    public companion object {
        /**
         * Create a [Question] with a specified [callback].
         */
        public infix fun question(setup: (Question) -> Unit): Question {
            return Question(setup)
        }
    }
}
