package com.github.benpollarduk.ktvn.prototyping.swing.components

import com.github.benpollarduk.ktvn.prototyping.swing.Severity

/**
 * Provides an interface for a text based terminal than can display events.
 */
interface EventTerminal {
    /**
     * Clear the terminal.
     */
    fun clear()

    /**
     * Print a [value] to the terminal with a specified [severity].
     */
    fun println(severity: Severity, value: String)
}
