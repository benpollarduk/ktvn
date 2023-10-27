package com.github.benpollarduk.ktvn.characters

/**
 * Provides a character with a specified [name].
 */
public class Character(public val name: String) {
    /**
     * Get the characters current [Emotion].
     */
    public var emotion: Emotion = BaseEmotions.none
        private set

    /**
     * Set the current [emotion].
     */
    public infix fun looks(emotion: Emotion) {
        this.emotion = emotion
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        println(line)
    }
}
