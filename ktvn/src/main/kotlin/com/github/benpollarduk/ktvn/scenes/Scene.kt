package com.github.benpollarduk.ktvn.scenes

import com.github.benpollarduk.ktvn.backgrounds.Background

/**
 * Provides a scene with a specified [background], [layout] and [content].
 */
public class Scene(
    public val background: Background,
    public val layout: Layout,
    private val content: SceneContent
) {
    /**
     * Begin the scene from a specified [startStep].
     */
    public fun begin(startStep: Int = 0) {
        for (i in startStep until content.size) {
            content[i]()
        }
    }
}
