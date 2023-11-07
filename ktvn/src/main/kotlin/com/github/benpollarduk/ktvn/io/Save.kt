package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.logic.structure.StoryPosition

/**
 * Provides a save with a specified [name], [flags] and [position].
 */
public data class Save(
    public val name: String,
    public val flags: Map<String, Boolean>,
    public val position: StoryPosition
) {
    public companion object {
        /**
         * Provides an empty save.
         */
        public val empty: Save = Save("", emptyMap(), StoryPosition.start)
    }
}
