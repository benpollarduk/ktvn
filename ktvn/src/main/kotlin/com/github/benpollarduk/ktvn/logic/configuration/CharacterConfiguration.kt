package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.CharacterAskListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.characters.SpeakListener
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a configuration for a characters.
 */
public interface CharacterConfiguration {
    /**
     * Get the acknowledgement listener for emote.
     */
    public val emoteAcknowledgementListener: AcknowledgeListener

    /**
     * Get the acknowledgement listener for speak.
     */
    public val speakAcknowledgementListener: AcknowledgeListener

    /**
     * Get the acknowledgement listener for animate.
     */
    public val animateAcknowledgementListener: AcknowledgeListener

    /**
     * Get the answer listener.
     */
    public val answerListener: AnswerListener

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
