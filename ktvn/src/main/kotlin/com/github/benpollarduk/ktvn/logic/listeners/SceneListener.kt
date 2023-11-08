package com.github.benpollarduk.ktvn.logic.listeners

import com.github.benpollarduk.ktvn.logic.structure.Scene

/**
 * Provides an interface for listeners to [Scene] change events.
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
