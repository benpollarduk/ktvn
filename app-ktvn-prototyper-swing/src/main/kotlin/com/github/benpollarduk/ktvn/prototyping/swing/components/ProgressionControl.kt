package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.logic.ProgressionMode

/**
 * Provides an interface for controlling progression.
 */
interface ProgressionControl {
    /**
     * Get the progression mode.
     */
    val mode: ProgressionMode

    /**
     * Get or set if force skip is used when [mode] is set to ProgressionMode.Skip.
     */
    var forceSkip: Boolean

    /**
     * Get or set the auto time in milliseconds when [mode] is set to ProgressionMode.Auto.
     */
    var autoTimeInMs: Long

    /**
     * Acknowledge anything that is pending acknowledgement.
     */
    fun acknowledge()

    /**
     * Set if acknowledgements are allowed.
     */
    fun allowAcknowledge(allow: Boolean)

    /**
     * Reset.
     */
    fun reset()
}
