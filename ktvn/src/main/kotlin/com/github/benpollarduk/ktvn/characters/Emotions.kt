package com.github.benpollarduk.ktvn.characters

/**
 * Provides a collection of emotions.
 */
public object Emotions {
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

    /**
     * Base emotion for excited.
     */
    public val excited: Emotion = object : Emotion {
        override fun toString(): String {
            return "excited"
        }
    }

    /**
     * Base emotion for angry.
     */
    public val angry: Emotion = object : Emotion {
        override fun toString(): String {
            return "angry"
        }
    }

    /**
     * Base emotion for confused.
     */
    public val confused: Emotion = object : Emotion {
        override fun toString(): String {
            return "confused"
        }
    }

    /**
     * Base emotion for concerned.
     */
    public val concerned: Emotion = object : Emotion {
        override fun toString(): String {
            return "concerned"
        }
    }

    /**
     * Base emotion for amused.
     */
    public val amused: Emotion = object : Emotion {
        override fun toString(): String {
            return "amused"
        }
    }
}
