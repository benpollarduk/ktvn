package com.github.benpollarduk.ktvn.text.sequencing

import com.github.benpollarduk.ktvn.text.frames.TextFrame

/**
 * Provides an interface for listeners to [SequencedTextController] events.
 */
public interface SequencedTextControllerListener {
    /**
     * Wait for an acknowledgement.
     */
    public fun waitFor()

    /**
     * Invokes the listener when started sequencing the specified [frame].
     */
    public fun startedFrame(frame: TextFrame)

    /**
     * Invokes the listener when finished sequencing the specified [frame].
     */
    public fun finishedFrame(frame: TextFrame)
}
