package com.github.benpollarduk.ktvn.text.sequencing

import com.github.benpollarduk.ktvn.text.frames.TextFrame

/**
 * Provides a class for sequenced text control using a [sequencer].
 */
public class SequencedTextController(private val sequencer: TextSequencer) {
    private var listener: SequencedTextControllerListener? = null

    /**
     * Get or set the delay between characters, in milliseconds.
     */
    public var msBetweenCharacters: Long
        get() = sequencer.msBetweenCharacters
        set(value) {
            sequencer.msBetweenCharacters = value
        }

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
     * Skip any running or pending sequencing.
     */
    public fun skip() {
        sequencer.requestAll()
    }

    /**
     * Stop any running sequencing.
     */
    public fun stop() {
        sequencer.cancel()
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

    public companion object {
        /**
         * Get the default milliseconds between characters.
         */
        public const val DEFAULT_MS_BETWEEN_CHARACTERS: Long = 50
    }
}
