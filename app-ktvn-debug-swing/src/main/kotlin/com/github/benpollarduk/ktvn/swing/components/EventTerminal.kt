package com.github.benpollarduk.ktvn.swing.components

import com.github.benpollarduk.ktvn.swing.Severity

/**
 * Provides an interface for a text based terminal than can display events.
 */
public interface EventTerminal {
    /**
     * Clear the terminal.
     */
    fun clear()

    /**
     * Print a [value] to the terminal with a specified [severity].
     */
    fun println(severity: Severity, value: String)
}
