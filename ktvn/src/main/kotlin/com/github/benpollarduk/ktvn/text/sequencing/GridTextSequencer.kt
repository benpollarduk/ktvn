package com.github.benpollarduk.ktvn.text.sequencing

import com.github.benpollarduk.ktvn.text.frames.CharacterPosition
import com.github.benpollarduk.ktvn.text.frames.TextFrame

/**
 * Provides a [TextSequencer] that is time based and dispatches lists of [CharacterPosition] for a grid based
 * arrangement. At each step of the sequence a list of [CharacterPosition] will be dispatched to the listener. The
 * delay between characters must be specified, in milliseconds, with [msBetweenCharacters]. A [listener] must be
 * provided.
 */
public class GridTextSequencer(
    override var msBetweenCharacters: Long,
    private val listener: (characters: List<CharacterPosition>) -> Unit
) : TextSequencer {
    private var isSequencing: Boolean = false
    private var forceAll: Boolean = false
    private var cancelled: Boolean = false

    override val sequencing: Boolean
        get() = isSequencing

    override fun requestAll() {
        forceAll = true
    }

    override fun cancel() {
        cancelled = true
    }

    override fun sequence(frame: TextFrame) {
        isSequencing = true
        cancelled = false

        val positions = frame.getCharacterPositions()
        var i = 0

        while (i < positions.size && !cancelled) {
            val charactersThisCycle = if (forceAll) {
                val start = i
                i = positions.size
                positions.subList(start, positions.size)
            } else {
                val start = i
                i++
                listOf(positions[start])
            }

            listener(charactersThisCycle)

            var invokeDelay = msBetweenCharacters > 0
            invokeDelay = invokeDelay && !forceAll
            invokeDelay = invokeDelay && i < positions.size - 1

            if (invokeDelay) {
                Thread.sleep(msBetweenCharacters)
            }
        }

        forceAll = false
        isSequencing = false
    }
}
