package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.logic.Ending
import com.github.benpollarduk.ktvn.logic.structure.StoryPosition

/**
 * Provides a save with a specified [name], [flags], [position], [totalSeconds] and list of [endingsReached].
 */
public data class Save(
    public val name: String,
    public val flags: Map<String, Boolean>,
    public val position: StoryPosition,
    public val totalSeconds: Long,
    public val endingsReached: List<Ending>
) {
    public companion object {
        /**
         * Provides an empty save.
         */
        public val empty: Save = Save("", emptyMap(), StoryPosition.start, 0, emptyList())
    }
}
