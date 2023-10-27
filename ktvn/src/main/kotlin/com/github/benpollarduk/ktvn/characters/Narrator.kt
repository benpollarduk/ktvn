package com.github.benpollarduk.ktvn.characters

/**
 * Provides a narrator with a specified listener for [narrate].
 */
public class Narrator(private val narrate: Narrates) {

    /**
     * Narrate a [line].
     */
    public infix fun narrates(line: String) {
        narrate(line)
    }
}
