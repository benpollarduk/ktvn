package com.github.benpollarduk.ktvn.swing.components

import java.awt.image.BufferedImage

/**
 * Provides an interface for a background.
 */
public interface Background {
    /**
     * Get the resolution width.
     */
    public val resolutionWidth: Int

    /**
     * Get the resolution height.
     */
    public val resolutionHeight: Int

    /**
     * Set the [image].
     */
    public fun setImage(image: BufferedImage)
}
