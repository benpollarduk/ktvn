package com.github.benpollarduk.ktvn.prototyping.swing.components

/**
 * Provides an interface for adjusting volume.
 */
interface VolumeControl {
    /**
     * Get or set the master volume.
     */
    var masterVolume: Double

    /**
     * Get or set the music volume.
     */
    var musicVolume: Double

    /**
     * Get or set the sound effect volume.
     */
    var soundEffectVolume: Double
}
