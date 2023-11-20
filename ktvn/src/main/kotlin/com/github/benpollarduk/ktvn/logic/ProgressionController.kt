package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantLock

/**
 * A controller for progression throughout a [Game].
 */
public class ProgressionController {
    private var acknowledgementReceived: CountDownLatch? = null
    private val lock: ReentrantLock = ReentrantLock()

    /**
     * Get or set the progression mode.
     */
    public var progressionMode: ProgressionMode = ProgressionMode.Skip(false)

    /**
     * Get the auto time controller. This controls auto acknowledgements.
     */
    public val autoTimeController: AutoTimeController = AutoTimeController()

    /**
     * Trigger the acknowledgement latch.
     */
    public fun triggerAcknowledgementLatch() {
        try {
            lock.lock()
            acknowledgementReceived?.countDown()
        } finally {
            lock.unlock()
        }
    }

    /**
     * Release the acknowledgement latch.
     */
    public fun releaseAcknowledgementLatch() {
        try {
            lock.lock()
            acknowledgementReceived = null
        } finally {
            lock.unlock()
        }
    }

    /**
     * Reset the acknowledgement latch.
     */
    public fun resetAcknowledgementLatch() {
        try {
            lock.lock()
            acknowledgementReceived = CountDownLatch(1)
        } finally {
            lock.unlock()
        }
    }

    /**
     * Await an acknowledgment. If [canSkipCurrentStep] is set true the current step is regarded as one that can be
     * skipped. A [cancellationToken] must be provided to support cancellation.
     */
    public fun awaitAcknowledgement(canSkipCurrentStep: Boolean, cancellationToken: CancellationToken) {
        resetAcknowledgementLatch()

        // some progression modes control flow differently
        when (val mode = progressionMode) {
            is ProgressionMode.Auto -> {
                if (autoTimeController.wait(mode.postDelayInMs, cancellationToken)) {
                    triggerAcknowledgementLatch()
                }
            }
            is ProgressionMode.Skip -> {
                if (canSkipCurrentStep || mode.skipUnseen) {
                    triggerAcknowledgementLatch()
                }
            }
            is ProgressionMode.WaitForConfirmation -> {
                // no handling
            }
        }

        // wait for the acknowledgement to be received
        acknowledgementReceived?.await()

        releaseAcknowledgementLatch()
    }
}
