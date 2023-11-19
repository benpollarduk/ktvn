package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.characters.AnswerListener
import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.NarratorAskListener
import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides a configuration for narration.
 */
public interface NarratorConfiguration {
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
