package com.github.benpollarduk.ktvn.characters

/**
 * Provides a library of base emotions.
 */
public object BaseEmotions {
    /**
     * No emotion.
     */
    public val none: Emotion = object : Emotion { }

    /**
     * Happy.
     */
    public val happy: Emotion = object : Emotion { }

    /**
     * Sad.
     */
    public val sad: Emotion = object : Emotion { }
}
