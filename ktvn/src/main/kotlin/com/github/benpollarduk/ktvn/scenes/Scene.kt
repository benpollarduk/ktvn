package com.github.benpollarduk.ktvn.scenes

import com.github.benpollarduk.ktvn.scenes.backgrounds.Background

public class Scene(
    public val background: Background,
    public val layout: Layout,
    public val callback: (Layout) -> Unit
) {
    public fun begin() {
        callback(layout)
    }
}
