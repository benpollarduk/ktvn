package com.github.benpollarduk.ktvn.logic.adapters

import com.github.benpollarduk.ktvn.logic.structure.ChapterListener
import com.github.benpollarduk.ktvn.logic.structure.SceneListener
import com.github.benpollarduk.ktvn.logic.structure.StepListener

/**
 * Provides an adapter for a story. The adapter allows for story events to be passed to a receiving class.
 */
public interface StoryAdapter {
    /**
     * Get the listener for chapter events.
     */
    public val chapterListener: ChapterListener

    /**
     * Get the listener for scene events.
     */
    public val sceneListener: SceneListener

    /**
     * Get the listener for step events.
     */
    public val stepListener: StepListener
}
