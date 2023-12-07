package com.github.benpollarduk.ktvn.layout

/**
 * Provides an interface for position. Positions are relative to the top left corner, top left ix 0,0, bottom right is
 * 1,1.
 */
public interface Position {
    /**
     * Get the normalized X position.
     */
    public val normalizedX: Double

    /**
     * Get the normalized Y position.
     */
    public val normalizedY: Double
}
