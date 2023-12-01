package com.github.benpollarduk.ktvn.text.sequencing

import com.github.benpollarduk.ktvn.text.frames.TextFrame

/**
 * Provides a [TextSequencer] that is time based and dispatches lists of [Char] to be appended. At each step of the
 * sequence a list of [Char] will be dispatched to the listener. The delay between characters must be specified, in
 * milliseconds, with [msBetweenCharacters]. A [listener] must be provided.
 */
public class AppenderTextSequencer(
    override var msBetweenCharacters: Long,
    private val listener: (characters: List<Char>) -> Unit
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

    @Suppress("NestedBlockDepth")
    override fun sequence(frame: TextFrame) {
        isSequencing = true
        cancelled = false

        val positions = frame.getCharacterPositions()
        var i = 0
        var lastRow = 0

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

            val characters: MutableList<Char> = mutableListOf()

            for (pos in charactersThisCycle) {
                // add newlines when the rows change
                characters.addAll(List(pos.row - lastRow) { '\n' })
                lastRow = pos.row
                characters.add(pos.character)
            }

            listener(characters)

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
