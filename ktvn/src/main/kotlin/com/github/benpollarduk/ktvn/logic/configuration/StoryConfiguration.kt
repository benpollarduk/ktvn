package com.github.benpollarduk.ktvn.logic.configuration

import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.SceneListener

/**
 * Provides a configuration for a story.
 */
public interface StoryConfiguration {
    /**
     * Get the listener for chapter events.
     */
    public val chapterListener: ChapterListener

    /**
     * Get the listener for scene events.
     */
    public val sceneListener: SceneListener
}
