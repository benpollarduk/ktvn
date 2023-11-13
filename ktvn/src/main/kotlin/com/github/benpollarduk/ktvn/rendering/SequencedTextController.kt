package com.github.benpollarduk.ktvn.rendering

import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.rendering.frames.TextFrame
import com.github.benpollarduk.ktvn.rendering.sequencing.TextSequencer

/**
 * Provides a class for sequenced text control using a [sequencer].
 */
public class SequencedTextController(private val sequencer: TextSequencer) {
    private var acknowledged: Boolean = false
    private var _acknowledgementRequired: Boolean = false
    private var finished: Boolean = false
    private val listeners: MutableList<SequencedTextDisplayListener> = mutableListOf()

    /**
     * Get if an acknowledgment is required.
     */
    public val acknowledgementRequired: Boolean
        get() = _acknowledgementRequired

    /**
     * Get if a sequencing is finished.
     */
    public val sequencingFinished: Boolean
        get() = finished

    private fun waitForAcknowledgeOrCancel(cancellationToken: CancellationToken): Boolean {
        _acknowledgementRequired = true

        listeners.forEach {
            it.acknowledgeRequiredChanged(acknowledgementRequired)
        }

        while (!acknowledged && !cancellationToken.wasCancelled) {
            Thread.sleep(WAIT_CHECK_FREQUENCY_IN_MS)
        }

        acknowledged = false
        _acknowledgementRequired = false

        listeners.forEach {
            it.acknowledgeRequiredChanged(acknowledgementRequired)
        }

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
     * Display a list of [frames]. A [cancellationToken] can be specified if cancellation may be required.
     */
    public fun display(frames: List<TextFrame>, cancellationToken: CancellationToken = CancellationToken()) {
        acknowledged = false

        for (frame in frames) {
            sequencer.sequence(frame, cancellationToken)

            val acknowledged = waitForAcknowledgeOrCancel(cancellationToken)

            if (!acknowledged) {
                return
            }
        }
    }

    public companion object {
        private const val WAIT_CHECK_FREQUENCY_IN_MS: Long = 10
    }
}
