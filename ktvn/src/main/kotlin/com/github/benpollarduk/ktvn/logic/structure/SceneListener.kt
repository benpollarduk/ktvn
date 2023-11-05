package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listening to [Scene] changes.
 */
public interface SceneListener {
    /**
     * Enter a [scene].
     */
    public fun enter(scene: Scene)

    /**
     * Exit a [scene].
     */
    public fun exit(scene: Scene)
}
