package com.github.benpollarduk.ktvn.logic

/**
 * Provides the ending of a story.
 */
public data class Ending(public val name: String, public val number: Int) {
    public companion object {
        /**
         * A default value for no ending.
         */
        public val noEnding: Ending = Ending("", -1)

        /**
         * A default ending.
         */
        public val default: Ending = Ending("Default Ending", 0)
    }
}
