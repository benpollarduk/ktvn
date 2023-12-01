package com.github.benpollarduk.ktvn.layout

/**
 * Provides an interface for position. This refers positions relative to the screen.
 */
public interface Position {
    /**
     * Get the X origin.
     */
    public val xOrigin: XOrigin

    /**
     * Get the Y origin.
     */
    public val yOrigin: YOrigin

    /**
     * Get the normalized X position.
     */
    public val normalizedX: Double

    /**
     * Get the normalized Y position.
     */
    public val normalizedY: Double
}
