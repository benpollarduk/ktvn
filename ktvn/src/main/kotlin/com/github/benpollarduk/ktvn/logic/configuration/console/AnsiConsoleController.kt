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
    private val sequencer = TimeBasedTextSequencer {
        for (position in it) {
            if (position.column == 0 && position.row == 0) {
                clear()
            }
            setCursorPosition(position.column + 1, position.row + 1)
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
        // ANSI escape code to clear the screen
        kotlin.io.print("\u001b[H\u001b[2J")
        // flush output
        System.out.flush()
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
     * Display a [string] directly on the console temporarily. The default 1000ms duration can be specified with
     * [durationInMs], in milliseconds.
     */
    public fun printlnDirectTemp(string: String, durationInMs: Long = 1000) {
        clear()
        println(string)

        if (durationInMs > 0) {
            Thread.sleep(durationInMs)
        }
    }

    public companion object {
        /**
         * Get the default width.
         */
        public const val DEFAULT_WIDTH: Int = 100

        /**
         * Get the default lines.
         */
        public const val DEFAULT_LINES: Int = 10
    }
}
