package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a configuration for layout.
 */
public interface LayoutConfiguration {
    /**
     * Get the acknowledgement listener for move.
     */
    public val movesAcknowledgementListener: AcknowledgeListener

    /**
     * Get the move listener.
     */
    public val moveListener: MoveListener
}
