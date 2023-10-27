package com.github.benpollarduk.ktvn.logic

/**
 * A story, consisting of one or more [acts].
 */
public class Story(private val acts: List<Act>) {
    /**
     * Get the index of the current [Act].
     */
    public var indexOfCurrentAct: Int = 0
        private set

    /**
     * Get the current position within this story.
     */
    public val currentPosition: StoryPosition
        get() {
            val scene = acts[indexOfCurrentAct].currentScene
            val step = scene.indexOfCurrentStep
            return StoryPosition(indexOfCurrentAct, acts[indexOfCurrentAct].indexOfCurrentScene, step)
        }

    /**
     * Begin the [Story]. The [storyPosition] can be optionally specified.
     **/
    public fun begin(storyPosition: StoryPosition = StoryPosition.start) {
        for (i in storyPosition.act until acts.size) {
            indexOfCurrentAct = i

            if (i == storyPosition.act) {
                acts[i].begin(storyPosition.scene, storyPosition.step)
            } else {
                acts[i].begin()
            }
        }
    }
}
