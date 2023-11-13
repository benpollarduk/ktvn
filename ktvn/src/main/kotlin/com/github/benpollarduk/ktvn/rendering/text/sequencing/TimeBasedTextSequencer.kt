package com.github.benpollarduk.ktvn.rendering.text.sequencing

import com.github.benpollarduk.ktvn.rendering.text.frames.CharacterPosition
import com.github.benpollarduk.ktvn.rendering.text.frames.TextFrame

/**
 * Provides a [TextSequencer] that is time based. The delay between characters can be specified, in milliseconds, with
 * [msBetweenCharacters]. A [listener] must be provided.
 */
public class TimeBasedTextSequencer(
    private var msBetweenCharacters: Long = 100,
    private val listener: (characters: List<CharacterPosition>) -> Unit
) : TextSequencer {
    private var isSequencing : Boolean = false
    private var forceAll : Boolean = false

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
            val charactersThisCycle = if (forceAll){
                val start = i
                i = positions.size - 1
                positions.subList(start, positions.size - 1)
            } else {
                val start = i
                i++
                listOf(positions[start])
            }

            listener(charactersThisCycle)

            if (!forceAll && i < positions.size - 1 && msBetweenCharacters > 0) {
                Thread.sleep(msBetweenCharacters)
            }
        }

        forceAll = false
        isSequencing = false
    }
}