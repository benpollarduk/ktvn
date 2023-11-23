package com.github.benpollarduk.ktvn.swing.components

import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an interface for controlling progression.
 */
public interface ProgressionControl {
    /**
     * Get the progression mode.
     */
    public val mode: ProgressionMode

    /**
     * Get or set if force skip is used when [mode] is set to ProgressionMode.Skip.
     */
    public var forceSkip: Boolean

    /**
     * Get or set the auto time in milliseconds when [mode] is set to ProgressionMode.Auto.
     */
    public var autoTimeInMs: Long

    /**
     * Acknowledge anything that is pending acknowledgement.
     */
    public fun acknowledge()

    /**
     * Jump to a specified [chapter], [scene] and [step].
     */
    public fun jumpTo(chapter: Int, scene: Int, step: Int)
}
