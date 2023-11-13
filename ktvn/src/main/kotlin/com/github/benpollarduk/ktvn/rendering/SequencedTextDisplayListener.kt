package com.github.benpollarduk.ktvn.rendering

/**
 * Provides an interface for listeners to [SequencedTextControl] events.
 */
public interface SequencedTextDisplayListener {
    public fun acknowledgeRequiredChanged(required: Boolean)
}
