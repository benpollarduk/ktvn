package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides a class that can generate instances of [StepIdentifier].
 */
internal class StepIdentifierGenerator {
    private val chapters: MutableList<Chapter> = mutableListOf()
    private val scenes: MutableMap<Scene, Int> = mutableMapOf()

    /**
     * Reset generation.
     */
    internal fun reset() {
        chapters.clear()
        scenes.clear()
    }

    /**
     * Get the next identifier.
     */
    internal fun next(chapter: Chapter, scene: Scene): StepIdentifier {
        if (!chapters.contains(chapter)) {
            chapters.add(chapter)
        }

        val current = scenes[scene]
        if (current == null) {
            scenes[scene] = 0
        } else {
            scenes[scene] = current + 1
        }

        return StepIdentifier(chapters.indexOf(chapter), scenes.keys.indexOf(scene), scenes[scene] ?: 0)
    }
}
