package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listening for [Chapter] changes.
 */
public interface ChapterListener {
    /**
     * Enter a [chapter].
     */
    public fun enter(chapter: Chapter)

    /**
     * Exit a [chapter].
     */
    public fun exit(chapter: Chapter)
}
