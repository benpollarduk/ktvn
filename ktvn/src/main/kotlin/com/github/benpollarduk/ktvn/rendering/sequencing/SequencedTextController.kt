package com.github.benpollarduk.ktvn.rendering.sequencing

import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.rendering.frames.TextFrame

/**
 * Provides a class for sequenced text control using a [sequencer].
 */
public class SequencedTextController(private val sequencer: TextSequencer) {
    private var acknowledged: Boolean = false
    private var acknowledgementRequired: Boolean = false
    private var finished: Boolean = false
    private val listeners: MutableList<SequencedTextDisplayListener> = mutableListOf()

    /**
     * Get if a sequencing is finished.
     */
    public val sequencingFinished: Boolean
        get() = finished

    private fun waitForAcknowledgeOrCancel(cancellationToken: CancellationToken): Boolean {
        while (!acknowledged && !cancellationToken.wasCancelled) {
            Thread.sleep(WAIT_CHECK_FREQUENCY_IN_MS)
        }

        acknowledged = false
        acknowledgementRequired = false

        return !cancellationToken.wasCancelled
    }

    /**
     * Add a [listener].
     */
    public fun addListener(listener: SequencedTextDisplayListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    /**
     * Remove a [listener].
     */
    public fun removeListener(listener: SequencedTextDisplayListener) {
        listeners.remove(listener)
    }

    /**
     * Acknowledge a pending acknowledgment. If the [sequencer] is sequencing then a request for all characters to be
     * published will be made. If no acknowledgment is required then this request will be ignored.
     */
    public fun acknowledge() {
        if (sequencer.sequencing) {
            sequencer.requestAll()
        } else if (acknowledgementRequired) {
            acknowledged = true
        }
    }

    /**
     * Render a collection of [frames]. A [cancellationToken] can be specified if cancellation may be required.
     */
    public fun render(frames: Collection<TextFrame>, cancellationToken: CancellationToken = CancellationToken()) {
        acknowledged = false

        for (frame in frames) {
            listeners.forEach { it.startedFrame(frame) }
            sequencer.sequence(frame, cancellationToken)
            acknowledgementRequired = !cancellationToken.wasCancelled
            listeners.forEach { it.finishedFrame(frame, acknowledgementRequired) }

            if (!waitForAcknowledgeOrCancel(cancellationToken)) {
                return
            }
        }
    }

    public companion object {
        private const val WAIT_CHECK_FREQUENCY_IN_MS: Long = 10
    }
}
