package com.github.benpollarduk.ktvn.rendering.sequencing

import com.github.benpollarduk.ktvn.rendering.frames.TextFrame

/**
 * Provides an interface for listeners to [SequencedTextController] events.
 */
public interface SequencedTextDisplayListener {
    /**
     * Invokes the listener when started sequencing the specified [frame].
     */
    public fun startedFrame(frame: TextFrame)

    /**
     * Invokes the listener when finished sequencing the specified [frame]. [acknowledgementRequired] specifies if
     * acknowledgement to the frame is required before execution continues.
     */
    public fun finishedFrame(frame: TextFrame, acknowledgementRequired: Boolean)
}
