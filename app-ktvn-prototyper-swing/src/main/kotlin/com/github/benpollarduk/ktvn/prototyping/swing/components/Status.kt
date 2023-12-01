package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.logic.structure.StepIdentifier

/**
 * Provides an interface for a display of sequenced text output.
 */
interface Status {
    /**
     * Update the position.
     */
    fun updatePosition(position: StepIdentifier)

    /**
     * Reset.
     */
    fun reset()
}
