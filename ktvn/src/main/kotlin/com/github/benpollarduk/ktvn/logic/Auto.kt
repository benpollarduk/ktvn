package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import kotlin.math.min

/**
 * Provides a class for controlling 'auto' functionality. Auto will auto-acknowledge after a specified time.
 */
public class Auto {
    /**
     * Wait for a specified [timeInMs]. A [cancellationToken] must be provided to support cancellation. Returns true
     * if the wait completed without being cancelled, else false.
     */
    public fun wait(timeInMs: Long, cancellationToken: CancellationToken): Boolean {
        val startTime = System.currentTimeMillis()
        var currentTime = startTime

        while (currentTime - startTime < timeInMs && !cancellationToken.wasCancelled) {
            val remaining = timeInMs - (currentTime - startTime)
            val delay = min(remaining, DELAY_BETWEEN_CHECKS_IN_MS)

            if (delay > 0) {
                Thread.sleep(delay)
                currentTime = System.currentTimeMillis()
            }
        }

        return !cancellationToken.wasCancelled
    }

    public companion object {
        private const val DELAY_BETWEEN_CHECKS_IN_MS: Long = 10
    }
}
