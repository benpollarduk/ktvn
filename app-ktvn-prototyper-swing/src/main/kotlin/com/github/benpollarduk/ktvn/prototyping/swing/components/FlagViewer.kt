package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.logic.Flags

/**
 * Provides an interface for viewing flags.
 */
interface FlagViewer {
    /**
     * Update the flags.
     */
    fun updateFlags(flags: Flags)

    /**
     * Reset.
     */
    fun reset()
}
