package com.github.benpollarduk.ktvn.text.log

import java.lang.StringBuilder
import java.util.LinkedList
import java.util.Queue

/**
 * Provides an event log. The [maximumCapacity] specifies the maximum number of elements this [Log] can contain, after
 * which values are discarded as new ones are added, in a first in first out manner.
 */
public class Log(public val maximumCapacity: Int = DEFAULT_CAPACITY) {
    private val queue: Queue<LogElement> = LinkedList()

    /**
     * Get the number of logged elements.
     */
    public val count: Int
        get() = queue.size

    /**
     * Clear all elements from the log.
     */
    public fun clear() {
        queue.clear()
    }

    /**
     * Add an element to the log.
     */
    public fun add(logElement: LogElement) {
        if (maximumCapacity != NO_LIMIT && queue.size == maximumCapacity) {
            queue.remove()
        }

        queue.add(logElement)
    }

    /**
     * Get the contents of the log, as and array.
     */
    public fun toArray(): Array<LogElement> {
        return queue.toTypedArray()
    }

    override fun toString(): String {
        val builder = StringBuilder()

        val log = toArray()
        log.forEach {
            when (it) {
                is LogElement.NarratorLog -> {
                    builder.appendLine(it.value)
                }
                is LogElement.CharacterLog -> {
                    builder.appendLine("${it.character.name}: ${it.value}")
                }
                is LogElement.StringLog -> {
                    builder.appendLine(it.value)
                }
            }
            builder.appendLine()
        }

        return builder.toString()
    }

    public companion object {
        /**
         * Gets the constant value for no limit.
         */
        public const val NO_LIMIT: Int = -1

        /**
         * Gets the default maximum capacity.
         */
        public const val DEFAULT_CAPACITY: Int = 250
    }
}
