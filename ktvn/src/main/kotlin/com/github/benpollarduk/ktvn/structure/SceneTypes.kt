package com.github.benpollarduk.ktvn.structure

/**
 * Provides a collection of scene types.
 */
public object SceneTypes {
    /**
     * A conversational scene.
     */
    public val dialog: SceneType = object : SceneType {
        override fun toString(): String {
            return "dialog"
        }
    }

    /**
     * A narrative scene.
     */
    public val narrative: SceneType = object : SceneType {
        override fun toString(): String {
            return "narrative"
        }
    }
}
