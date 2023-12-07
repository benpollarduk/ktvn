package com.github.benpollarduk.ktvn.prototyping.swing

/**
 * Provides a progress update with a [value] as a normalise value and an indicator if the update is [finished]
 */
data class ProgressUpdate(public val value: Double, public val finished: Boolean)
