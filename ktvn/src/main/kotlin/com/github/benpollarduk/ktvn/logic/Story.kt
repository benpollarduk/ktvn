package com.github.benpollarduk.ktvn.logic

/**
 * A story, consisting of one or more [acts].
 */
public class Story(private val acts: List<Act>) {
    /**
     * Begin the [Story]. The [act], [scene] and [step] can be optionally specified.
     **/
    public fun begin(act: Int = 0, scene: Int = 0, step: Int = 0) {
        for (i in act until acts.size) {
            if (i == act) {
                acts[i].begin(scene, step)
            } else {
                acts[i].begin()
            }
        }
    }
}
