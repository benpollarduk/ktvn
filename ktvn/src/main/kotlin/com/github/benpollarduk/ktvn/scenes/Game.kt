package com.github.benpollarduk.ktvn.scenes

public class Game(private val acts: List<Act>) {
    public fun begin() {
        acts.forEach { it.begin() }
    }
}
