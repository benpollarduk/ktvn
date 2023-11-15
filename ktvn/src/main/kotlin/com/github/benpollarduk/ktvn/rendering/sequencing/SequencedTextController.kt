package com.github.benpollarduk.ktvn.rendering.sequencing

import com.github.benpollarduk.ktvn.rendering.frames.TextFrame

/**
 * Provides a class for sequenced text control using a [sequencer].
 */
public class SequencedTextController(private val sequencer: TextSequencer) {
    private var listener: SequencedTextControllerListener? = null

    /**
     * Get if sequencing is running.
     */
    public val sequencing: Boolean
        get() = sequencer.sequencing

    /**
     * Set the [listener].
     */
    public fun addListener(listener: SequencedTextControllerListener) {
        this.listener = listener
    }

    /**
     * Remove the [listener].
     */
    public fun removeListener() {
        this.listener = null
    }

    /**
     * Skip any running sequencing.
     */
    public fun skip() {
        if (sequencer.sequencing) {
            sequencer.requestAll()
        }
    }

    /**
     * Render a collection of [frames].
     */
    public fun render(frames: Collection<TextFrame>) {
        for (frame in frames) {
            listener?.startedFrame(frame)
            sequencer.sequence(frame)
            listener?.finishedFrame(frame)

            if (frame != frames.last()) {
                listener?.waitFor()
            }
        }
    }
}
