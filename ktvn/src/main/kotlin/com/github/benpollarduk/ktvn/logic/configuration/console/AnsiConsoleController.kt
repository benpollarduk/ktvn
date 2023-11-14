package com.github.benpollarduk.ktvn.logic.configuration.console

import com.github.benpollarduk.ktvn.rendering.SequencedTextController
import com.github.benpollarduk.ktvn.rendering.SequencedTextDisplayListener
import com.github.benpollarduk.ktvn.rendering.frames.CharacterConstrainedTextFrame
import com.github.benpollarduk.ktvn.rendering.frames.TextFrameParameters
import com.github.benpollarduk.ktvn.rendering.sequencing.TimeBasedTextSequencer

/**
 * A class that functions as a controller for a console that is ANSI compatible.
 */
public class AnsiConsoleController(
    private val parameters: TextFrameParameters = TextFrameParameters(DEFAULT_WIDTH, DEFAULT_LINES)
) {
    private val sequencer = TimeBasedTextSequencer(DEFAULT_MS_BETWEEN_CHARACTERS) {
        for (position in it) {
            setCursorPosition(position.column, position.row)
            setCursorVisibility(false)
            print(position.character)
        }
    }

    private val display = SequencedTextController(sequencer)

    init {
        val listener: SequencedTextDisplayListener = object : SequencedTextDisplayListener {
            override fun acknowledgeRequiredChanged(required: Boolean) {
                display.acknowledge()
            }
        }
        display.addListener(listener)
    }

    /**
     * Set the cursor to a specified [x] and [y] position.
     */
    private fun setCursorPosition(x: Int, y: Int) {
        // use ANSI escape codes to set the cursor position
        kotlin.io.print("\u001b[$y;${x}H")
    }

    /**
     * Wait for the enter key to be pressed.
     */
    public fun waitForEnter() {
        readln()
    }

    /**
     * Wait for entry followed by the enter key.
     */
    public fun waitForInput(): String {
        return readln()
    }

    /**
     * Set the cursor visibility. Setting [show] to true will show the cursor, false will hide it.
     */
    public fun setCursorVisibility(show: Boolean) {
        // use ANSI escape codes to hide or show the cursor
        kotlin.io.print(if (show) "\u001b[?25h" else "\u001b[?25l")
    }

    /**
     * Clear the console.
     */
    public fun clear() {
        // windows terminal doesn't clear properly with ANSI...
        if (System.getProperty("os.name").contains("Windows")) {
            ProcessBuilder("cmd", "/character", "cls").inheritIO().start().waitFor()
        } else {
            // ANSI escape code to clear the screen
            kotlin.io.print("\u001b[H\u001b[2J")
            // flush output
            System.out.flush()
        }
    }

    /**
     * Print a [string] to the console.
     */
    public fun print(string: String) {
        val frames = CharacterConstrainedTextFrame.create(
            string,
            parameters
        )
        display.display(frames)
    }

    /**
     * Println a [string] to the console.
     */
    public fun println(string: String) {
        print("$string\n")
    }

    public companion object {
        /**
         * Get the default width.
         */
        public const val DEFAULT_WIDTH: Int = 50

        /**
         * Get the default lines.
         */
        public const val DEFAULT_LINES: Int = 10

        /**
         * Get the default milliseconds between characters.
         */
        public const val DEFAULT_MS_BETWEEN_CHARACTERS: Long = 50
    }
}
