package com.github.benpollarduk.ktvn.logic.engines.ansiConsole

/**
 * Provides an implementation of a [ConsoleAdapter] for an ANSI compatible console.
 */
public class AnsiConsoleAdapter : ConsoleAdapter {
    override fun print(line: String) {
        kotlin.io.print(line)
    }

    override fun readln(): String {
        return kotlin.io.readln()
    }
}
