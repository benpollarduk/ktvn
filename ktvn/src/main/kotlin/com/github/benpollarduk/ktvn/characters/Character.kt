package com.github.benpollarduk.ktvn.characters

/**
 * Provides a character with a specified [name]. Listeners for [speaks] and [emotes] must be specified.
 */
public class Character(
    public val name: String,
    private val speaks: Speaks,
    private val emotes: Emotes
) {
    /**
     * Get the characters current [Emotion].
     */
    public var emotion: Emotion = BaseEmotions.normal
        private set

    /**
     * Set the current [emotion].
     */
    public infix fun looks(emotion: Emotion) {
        this.emotion = emotion
        emotes(this, emotion)
    }

    /**
     * Say a [line].
     */
    public infix fun says(line: String) {
        speaks(this, line)
    }
}
