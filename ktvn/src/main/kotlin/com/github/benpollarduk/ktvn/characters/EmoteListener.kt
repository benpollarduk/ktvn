package com.github.benpollarduk.ktvn.characters

import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an interface for listeners to emote events.
 */
public interface EmoteListener {
    /**
     * Invoke the listener with a specified [character], [emotion] and [acknowledgement].
     */
    public fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener)
}
