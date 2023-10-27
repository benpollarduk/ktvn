package com.github.benpollarduk.ktvn.characters

/**
 * Provides a library of base emotions.
 */
public object BaseEmotions {
    /**
     * Base emotion for normal.
     */
    public val normal: Emotion = object : Emotion {
        override fun toString(): String {
            return "normal"
        }
    }

    /**
     * Base emotion for happy.
     */
    public val happy: Emotion = object : Emotion {
        override fun toString(): String {
            return "happy"
        }
    }

    /**
     * Base emotion for sad.
     */
    public val sad: Emotion = object : Emotion {
        override fun toString(): String {
            return "sad"
        }
    }
}
