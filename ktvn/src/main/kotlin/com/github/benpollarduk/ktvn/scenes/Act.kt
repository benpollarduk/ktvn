package com.github.benpollarduk.ktvn.scenes

public class Act(private val scenes: List<Scene>) {
    public fun begin() {
        scenes.forEach { it.begin() }
    }
}
