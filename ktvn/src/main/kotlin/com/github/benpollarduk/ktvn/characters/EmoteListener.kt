package com.github.benpollarduk.ktvn.characters

/**
 * Provides an interface for listeners to emote events.
 */
public fun interface EmoteListener {
    /**
     * Invoke the listener with a specified [character] and [emotion].
     */
    public fun emote(character: Character, emotion: Emotion)
}
