package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides a class that can map instances of [Step] to [StepIdentifier].
 */
internal class StepIdentifierMapper {
    /**
     * Map all [StepIdentifier] for a [scene].
     */
    private fun map(map: MutableMap<Step, StepIdentifier>, chapterIndex: Int, sceneIndex: Int, scene: Scene) {
        val steps = scene.getAllSteps()
        for (step in steps.indices) {
            map[steps[step]] = StepIdentifier(chapterIndex + 1, sceneIndex + 1, step + 1)
        }
    }

    /**
     * Map all [StepIdentifier] for a [chapter].
     */
    private fun map(map: MutableMap<Step, StepIdentifier>, chapterIndex: Int, chapter: Chapter) {
        val scenes = chapter.getAllScenes()
        for (scene in scenes.indices) {
            map(map, chapterIndex, scene, scenes[scene])
        }
    }

    /**
     * Map all [StepIdentifier] for a [story].
     */
    internal fun map(story: Story): Map<Step, StepIdentifier> {
        val map: MutableMap<Step, StepIdentifier> = mutableMapOf()
        val chapters = story.getAllChapters()
        for (chapter in chapters.indices) {
            map(map, chapter, chapters[chapter])
        }
        return map.toMap()
    }
}
