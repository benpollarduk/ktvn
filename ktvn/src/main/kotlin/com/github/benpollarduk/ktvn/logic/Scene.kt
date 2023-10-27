package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.backgrounds.Background
import com.github.benpollarduk.ktvn.layout.Layout

/**
 * Provides a scene with a specified [background], [layout] and [content].
 */
public class Scene(
    public val background: Background,
    public val layout: Layout,
    private val content: SceneContent
) {
    /**
     * Get the index of the current [Step].
     */
    public var indexOfCurrentStep: Int = 0
        private set

    /**
     * Begin the scene with specified [flags] from a specified [startStep].
     */
    public fun begin(flags: Flags, startStep: Int = 0) {
        for (i in startStep until content.size) {
            indexOfCurrentStep = i

            content[i](flags)
        }
    }
}
