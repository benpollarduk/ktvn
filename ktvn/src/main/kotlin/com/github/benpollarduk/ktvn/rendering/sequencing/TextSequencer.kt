package com.github.benpollarduk.ktvn.rendering.sequencing

import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.rendering.frames.TextFrame

/**
 * Provides an interface for sequencers for displaying [TextFrame].
 */
public interface TextSequencer {
    /**
     * Get if sequencing is currently running.
     */
    public val sequencing: Boolean

    /**
     * Request all characters now.
     */
    public fun requestAll()

    /**
     * Start sequencing characters for a specified [frame]. A [cancellationToken] must be provided to support
     * cancellation.
     */
    public fun sequence(frame: TextFrame, cancellationToken: CancellationToken)
}
