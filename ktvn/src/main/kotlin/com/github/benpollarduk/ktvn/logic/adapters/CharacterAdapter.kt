package com.github.benpollarduk.ktvn.logic.adapters

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.CharacterAskListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.SpeakListener

/**
 * Provides an adapter for a characters. The adapter allows for character events to be passed to a receiving class.
 */
public interface CharacterAdapter {
    /**
     * Get the ask listener.
     */
    public val askListener: CharacterAskListener

    /**
     * Get the emote listener.
     */
    public val emoteListener: EmoteListener

    /**
     * Get the animate listener.
     */
    public val animateListener: AnimateListener

    /**
     * Get the speak listener.
     */
    public val speakListener: SpeakListener
}
