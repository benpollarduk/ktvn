package com.github.benpollarduk.ktvn.logic.adapters

import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.NarratorAskListener
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an adapter for narration. The adapter allows for narrator events to be passed to a receiving class.
 */
public interface NarratorAdapter {
    /**
     * Get the acknowledgement listener for narrate.
     */
    public val narrateAcknowledgementListener: AcknowledgeListener

    /**
     * Get the answer listener.
     */
    public val answerListener: AnswerListener

    /**
     * Get the ask listener.
     */
    public val askListener: NarratorAskListener

    /**
     * Get the narrate listener.
     */
    public val narrateListener: NarrateListener
}
