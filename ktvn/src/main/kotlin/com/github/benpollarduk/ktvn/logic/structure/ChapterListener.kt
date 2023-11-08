package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listening for [Chapter] changes.
 */
public interface ChapterListener {
    /**
     * Invoke the listener to notify entry of a specified [chapter].
     */
    public fun enter(chapter: Chapter)

    /**
     * Invoke the listener to notify exit of a specified [chapter].
     */
    public fun exit(chapter: Chapter)
}
