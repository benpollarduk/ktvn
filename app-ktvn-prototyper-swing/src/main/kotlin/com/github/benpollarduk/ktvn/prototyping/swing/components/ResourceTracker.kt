package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.prototyping.swing.ResourceType

/**
 * Provides an interface for tracking resources.
 */
interface ResourceTracker {
    /**
     * Register that a required resource was found.
     */
    fun registerResourceLocated(key: String, requiredBy: String, type: ResourceType)

    /**
     * Register that a required resource was missing.
     */
    fun registerResourceMissing(key: String, requiredBy: String, type: ResourceType)

    /**
     * Reset.
     */
    fun reset()
}
