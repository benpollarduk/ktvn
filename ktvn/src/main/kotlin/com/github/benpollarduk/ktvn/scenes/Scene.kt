package com.github.benpollarduk.ktvn.scenes

import com.github.benpollarduk.ktvn.backgrounds.Background
import com.github.benpollarduk.ktvn.steps.Step

public class Scene(
    public val background: Background,
    public val layout: Layout,
    private val steps: List<Step>
) {
    public fun begin(startStep: Int = 0) {
        for (i in startStep..steps.size) {
            steps[i]()
        }
    }
}
