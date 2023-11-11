package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listeners for [Chapter] change events.
 */
public interface ChapterListener {
    /**
     * Invoke the listener to notify entry of a specified [chapter] with a specified [transition].
     */
    public fun enter(chapter: Chapter, transition: ChapterTransition)

    /**
     * Invoke the listener to notify exit of a specified [chapter].
     */
    public fun exit(chapter: Chapter)
}
