package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for different types of [Scene].
 */
public sealed interface SceneType {
    /**
     * A conversational scene.
     */
    public data object Dialog : SceneType

    /**
     * A narrative scene.
     */
    public data object Narrative : SceneType
}
