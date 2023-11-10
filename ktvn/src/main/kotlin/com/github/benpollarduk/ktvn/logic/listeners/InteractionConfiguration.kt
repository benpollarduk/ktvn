package com.github.benpollarduk.ktvn.logic.listeners

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
