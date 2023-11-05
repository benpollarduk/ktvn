package com.github.benpollarduk.ktvn.logic.listeners

/**
 * Provides a provider for listeners.
 */
public interface ListenerProvider {
    /**
     * Get the [Acknowledges] listener for emotes.
     */
    public val emotesAcknowledgement: Acknowledges

    /**
     * Get the [Acknowledges] listener for speaks.
     */
    public val speaksAcknowledgement: Acknowledges

    /**
     * Get the [Acknowledges] listener for moves.
     */
    public val movesAcknowledgement: Acknowledges

    /**
     * Get the [Answers] listener.
     */
    public val answers: Answers

    /**
     * Get the [Asks] listener.
     */
    public val asks: Asks

    /**
     * Get the [Emotes] listener.
     */
    public val emotes: Emotes

    /**
     * Get the [Narrates] listener.
     */
    public val narrates: Narrates

    /**
     * Get the [Moves] listener.
     */
    public val moves: Moves

    /**
     * Get the [Speaks] listener.
     */
    public val speaks: Speaks
}
