package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.scenes.Scene

public class Act(private val scenes: List<Scene>) {
    public fun begin() {
        scenes.forEach { it.begin() }
    }
}
