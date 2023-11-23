package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listeners for [Story] change events.
 */
public interface StoryListener {
    /**
     * Invoke the listener to notify entry of a specified [story]..
     */
    public fun enter(story: Story)

    /**
     * Invoke the listener to notify exit of a specified [story].
     */
    public fun exit(story: Story)
}
