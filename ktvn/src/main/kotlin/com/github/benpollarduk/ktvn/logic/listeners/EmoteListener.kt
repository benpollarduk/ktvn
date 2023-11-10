package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion

/**
 * Provides an interface for listeners to emote events.
 */
public interface EmoteListener {
    /**
     * Invoke the listener with a specified [character], [emotion] and [acknowledgement].
     */
    public fun emote(character: Character, emotion: Emotion, acknowledgement: AcknowledgeListener)
}
