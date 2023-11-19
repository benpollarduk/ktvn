package com.github.benpollarduk.ktvn.logic

/**
 * Provides an interface for progression modes.
 */
public sealed interface ProgressionMode {
    public data object WaitForConfirmation : ProgressionMode
    public data class Skip(public var skipUnseen: Boolean) : ProgressionMode
    public data class Auto(public var postDelayInMs: Long) : ProgressionMode
}
