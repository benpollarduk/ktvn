package com.github.benpollarduk.ktvn.logic.structure

/**
 * Provides an interface for listening to [Scene] changes.
 */
public interface SceneListener {
    /**
     * Invoke the listener to notify entry of a specified [scene].
     */
    public fun enter(scene: Scene)

    /**
     * Invoke the listener to notify exit of a specified [scene].
     */
    public fun exit(scene: Scene)
}
