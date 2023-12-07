package com.github.benpollarduk.ktvn.prototyping.swing.animations

import com.github.benpollarduk.ktvn.prototyping.swing.ProgressUpdate
import java.awt.event.ActionEvent
import javax.swing.Timer

/**
 * Provides a controller for animation. The [durationInMs] specifies how long the animation should run for, in
 * milliseconds, and [framesPerSecond] specifies how many updates should be invoked per second. The [tickListener]
 * allows a listener to be specified that is called on each tick where the progress is passed.
 */
class AnimationController(
    private val durationInMs: Long,
    private val framesPerSecond: Int,
    private val tickListener: (ProgressUpdate) -> Unit
) {
    private var timer: Timer? = null

    /**
     * Start the animation.
     */
    fun start() {
        stop()
        var elapsed = 0
        val delay = MILLISECONDS_PER_SECOND / framesPerSecond
        timer = Timer(delay) { _: ActionEvent ->
            elapsed += delay
            val progress = elapsed.toDouble() / durationInMs
            val complete = progress >= 1.0
            if (complete) {
                timer?.stop()
            }
            tickListener(ProgressUpdate(if (complete) 1.0 else progress, complete))
        }
        timer?.start()
    }

    /**
     * Stop the animation.
     */
    fun stop() {
        timer?.stop()
        timer = null
    }

    companion object {
        /**
         * Get the number of milliseconds per second.
         */
        const val MILLISECONDS_PER_SECOND: Int = 1000

        /**
         * Get the default number of frames per second.
         */
        const val DEFAULT_FRAMES_PER_SECOND: Int = 40
    }
}
