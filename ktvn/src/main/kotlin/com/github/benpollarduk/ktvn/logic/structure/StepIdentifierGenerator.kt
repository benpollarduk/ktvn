package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides a class that can generate instances of [StepIdentifier].
 */
internal class StepIdentifierGenerator {
    private val data: MutableMap<Chapter, MutableMap<Scene, Int>> = mutableMapOf()

    /**
     * Reset generation.
     */
    internal fun reset() {
        data.clear()
    }

    /**
     * Get the next identifier.
     */
    internal fun next(chapter: Chapter, scene: Scene): StepIdentifier {
        if (!data.contains(chapter)) {
            data[chapter] = mutableMapOf()
        }

        val chapterIndex = data.keys.indexOf(chapter)
        val sceneMap = data[chapter] ?: mutableMapOf()
        val sceneIndex = if (sceneMap.keys.contains(scene)) sceneMap.keys.indexOf(scene) else sceneMap.keys.count()
        var step = sceneMap[scene] ?: 0
        step++
        sceneMap[scene] = step

        return StepIdentifier(chapterIndex + 1, sceneIndex + 1, step)
    }
}
