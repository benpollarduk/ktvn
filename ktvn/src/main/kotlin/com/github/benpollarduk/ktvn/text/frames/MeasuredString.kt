package com.github.benpollarduk.ktvn.text.frames

/**
 * Provides a structure for holding a [string] with a measured [width].
 */
internal data class MeasuredString(public val string: String, public val width: Int)
