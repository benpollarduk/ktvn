package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener
import com.github.benpollarduk.ktvn.characters.AnimateListener
import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.AskListener
import com.github.benpollarduk.ktvn.audio.AudioListener
import com.github.benpollarduk.ktvn.characters.EmoteListener
import com.github.benpollarduk.ktvn.layout.MoveListener
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.SpeakListener

/**
 * Provides an interaction configuration.
 */
public interface InteractionConfiguration {
    /**
     * Get the acknowledgement listener for emote.
     */
    public val emotesAcknowledgementListener: AcknowledgeListener

    /**
     * Get the acknowledgement listener for speak.
     */
    public val speaksAcknowledgementListener: AcknowledgeListener

    /**
     * Get the acknowledgement listener for move.
     */
    public val movesAcknowledgementListener: AcknowledgeListener

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
    public val askListener: AskListener

    /**
     * Get the emote listener.
     */
    public val emoteListener: EmoteListener

    /**
     * Get the animate listener.
     */
    public val animateListener: AnimateListener

    /**
     * Get the narrate listener.
     */
    public val narrateListener: NarrateListener

    /**
     * Get the move listener.
     */
    public val moveListener: MoveListener

    /**
     * Get the speak listener.
     */
    public val speakListener: SpeakListener

    /**
     * Get the audio listener.
     */
    public val audioListener: AudioListener
}
