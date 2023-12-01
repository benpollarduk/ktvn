package com.github.benpollarduk.ktvn.logic.adapters

import com.github.benpollarduk.ktvn.layout.MoveListener

/**
 * Provides an adapter for layout. The adapter allows for layout events to be passed to a receiving class.
 */
public interface LayoutAdapter {
    /**
     * Get the move listener.
     */
    public val moveListener: MoveListener
}
