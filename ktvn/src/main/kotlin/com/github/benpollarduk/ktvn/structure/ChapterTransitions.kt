package com.github.benpollarduk.ktvn.structure

/**
 * Provides a collection of chapter transitions.
 */
public object ChapterTransitions {
    /**
     * Instant.
     */
    public val instant: ChapterTransition = object : ChapterTransition {
        override fun toString(): String {
            return "instant"
        }
    }

    /**
     * Title.
     */
    public val title: ChapterTransition = object : ChapterTransition {
        override fun toString(): String {
            return "fade in"
        }
    }
}
