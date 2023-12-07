package com.github.benpollarduk.ktvn.structure

import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition

/**
 * Provides an interface for listeners to [Scene] change events.
 */
public interface SceneListener {
    /**
     * Invoke the listener to notify entry of a specified [scene] with a specified [transition].
     */
    public fun enter(scene: Scene, transition: SceneTransition)

    /**
     * Invoke the listener to notify exit of a specified [scene] with a specified [transition].
     */
    public fun exit(scene: Scene, transition: SceneTransition)

    /**
     * Invoke the listener to notify clearing of a specified [scene].
     */
    public fun clear(scene: Scene)
}
