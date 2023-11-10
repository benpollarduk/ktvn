package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.logic.structure.Chapter

/**
 * Provides an interface for listeners for [Chapter] change events.
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
