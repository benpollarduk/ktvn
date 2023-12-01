package com.github.benpollarduk.ktvn.logic.adapters

import com.github.benpollarduk.ktvn.characters.NarrateListener
import com.github.benpollarduk.ktvn.characters.NarratorAskListener

/**
 * Provides an adapter for narration. The adapter allows for narrator events to be passed to a receiving class.
 */
public interface NarratorAdapter {
    /**
     * Get the ask listener.
     */
    public val askListener: NarratorAskListener

    /**
     * Get the narrate listener.
     */
    public val narrateListener: NarrateListener
}
