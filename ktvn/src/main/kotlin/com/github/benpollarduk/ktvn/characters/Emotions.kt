package com.github.benpollarduk.ktvn.characters

/**
 * Provides a collection of emotions.
 */
public object Emotions {
    /**
     * Normal.
     */
    public val normal: Emotion = object : Emotion {
        override fun toString(): String {
            return "normal"
        }
    }

    /**
     * Happy.
     */
    public val happy: Emotion = object : Emotion {
        override fun toString(): String {
            return "happy"
        }
    }

    /**
     * Sad.
     */
    public val sad: Emotion = object : Emotion {
        override fun toString(): String {
            return "sad"
        }
    }

    /**
     * Excited.
     */
    public val excited: Emotion = object : Emotion {
        override fun toString(): String {
            return "excited"
        }
    }

    /**
     * Angry.
     */
    public val angry: Emotion = object : Emotion {
        override fun toString(): String {
            return "angry"
        }
    }

    /**
     * Confused.
     */
    public val confused: Emotion = object : Emotion {
        override fun toString(): String {
            return "confused"
        }
    }

    /**
     * Concerned.
     */
    public val concerned: Emotion = object : Emotion {
        override fun toString(): String {
            return "concerned"
        }
    }

    /**
     * Amused.
     */
    public val amused: Emotion = object : Emotion {
        override fun toString(): String {
            return "amused"
        }
    }
}
