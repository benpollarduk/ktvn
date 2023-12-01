package com.github.benpollarduk.ktvn.text.sequencing

import com.github.benpollarduk.ktvn.text.frames.TextFrame

/**
 * Provides an interface for sequencers for displaying [TextFrame].
 */
public interface TextSequencer {
    /**
     * Get if sequencing is currently running.
     */
    public val sequencing: Boolean

    /**
     * Get or set the delay between characters, in milliseconds.
     */
    public var msBetweenCharacters: Long

    /**
     * Request all characters now.
     */
    public fun requestAll()

    /**
     * Cancel all sequencing.
     */
    public fun cancel()

    /**
     * Start sequencing characters for a specified [frame].
     */
    public fun sequence(frame: TextFrame)
}
