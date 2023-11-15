package com.github.benpollarduk.ktvn.rendering.sequencing

import com.github.benpollarduk.ktvn.rendering.frames.CharacterPosition
import com.github.benpollarduk.ktvn.rendering.frames.TextFrame

/**
 * Provides a [TextSequencer] that is time based. The delay between characters can be specified, in milliseconds, with
 * [msBetweenCharacters]. A [listener] must be provided.
 */
public class TimeBasedTextSequencer(
    private var msBetweenCharacters: Long = DEFAULT_MS_BETWEEN_CHARACTERS,
    private val listener: (characters: List<CharacterPosition>) -> Unit
) : TextSequencer {
    private var isSequencing: Boolean = false
    private var forceAll: Boolean = false

    override val sequencing: Boolean
        get() = isSequencing

    override fun requestAll() {
        forceAll = true
    }

    override fun sequence(frame: TextFrame) {
        isSequencing = true

        val positions = frame.getCharacterPositions()
        var i = 0

        while (i < positions.size) {
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

    public companion object {
        /**
         * Get the default milliseconds between characters.
         */
        public const val DEFAULT_MS_BETWEEN_CHARACTERS: Long = 50
    }
}
