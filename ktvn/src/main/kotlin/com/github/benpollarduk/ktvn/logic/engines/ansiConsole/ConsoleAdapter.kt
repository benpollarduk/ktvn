package com.github.benpollarduk.ktvn.logic.engines.ansiConsole

/**
 * Provides an adapter for a console.
 */
public interface ConsoleAdapter {
    /**
     * Print a [line] to standard output.
     */
    public fun print(line: String)

    /**
     * Read a line from standard input.
     */
    public fun readln(): String
}
