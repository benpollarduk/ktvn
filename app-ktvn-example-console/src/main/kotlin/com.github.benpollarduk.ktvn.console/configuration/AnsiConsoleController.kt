package com.github.benpollarduk.ktvn.console.configuration

import com.github.benpollarduk.ktvn.rendering.SequencedTextController
import com.github.benpollarduk.ktvn.rendering.SequencedTextDisplayListener
import com.github.benpollarduk.ktvn.rendering.frames.CharacterConstrainedTextFrame
import com.github.benpollarduk.ktvn.rendering.frames.TextFrameParameters
import com.github.benpollarduk.ktvn.rendering.sequencing.TimeBasedTextSequencer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantLock

/**
 * A class that functions as a controller for a console that is ANSI compatible.
 */
internal class AnsiConsoleController(
    private val parameters: TextFrameParameters = TextFrameParameters(DEFAULT_WIDTH, DEFAULT_LINES)
) {
    private var isProcessingInput = false
    private var input = ""
    private var inputReceived : CountDownLatch? = null
    private val lock = ReentrantLock()

    // the sequencer is responsible for sequencing the characters correctly
    private val sequencer = TimeBasedTextSequencer {
        for (position in it) {
            if (position.column == 0 && position.row == 0) {
                clear()
            }
            setCursorPosition(position.column + 1, position.row + 1)
            print(position.character)
        }
    }

    // the display is responsible for controlling the display of sequenced text
    private val display = SequencedTextController(sequencer)

    init {
        val listener: SequencedTextDisplayListener = object : SequencedTextDisplayListener {
            override fun acknowledgeRequiredChanged(required: Boolean) {
                display.acknowledge()
            }
        }
        display.addListener(listener)
        setCursorVisibility(false)
    }

    /**
     * Set the cursor to a specified [x] and [y] position.
     */
    private fun setCursorPosition(x: Int, y: Int) {
        // use ANSI escape codes to set the cursor position
        kotlin.io.print("\u001b[$y;${x}H")
    }

    /**
     * Set the cursor visibility. Setting [show] to true will show the cursor, false will hide it.
     */
    private fun setCursorVisibility(show: Boolean) {
        // use ANSI escape codes to hide or show the cursor
        kotlin.io.print(if (show) "\u001b[?25h" else "\u001b[?25l")
    }

    /**
     * Set the input value to a specified [input].
     */
    private fun setInput(input: String) {
        try {
            lock.lock()
            this.input = input
        } finally {
            lock.unlock()
        }
    }

    /**
     * Begin processing console input.
     */
    internal fun beginProcessingInput() {
        isProcessingInput = true

        while (isProcessingInput) {
            val input = readln()
            setInput(input)
            display.acknowledge()

            try {
                lock.lock()
                inputReceived?.countDown()
            } finally {
                lock.unlock()
            }
        }
    }

    /**
     * End processing of console input.
     */
    internal fun endProcessingInput() {
        isProcessingInput = false
    }

    /**
     * Wait for the enter key to be pressed.
     */
    internal fun waitForEnter() {
        try {
            lock.lock()
            inputReceived = CountDownLatch(1)
        } finally {
            lock.unlock()
        }

        inputReceived?.await()

        try {
            lock.lock()
            inputReceived = null
        } finally {
            lock.unlock()
        }
    }

    /**
     * Wait for entry followed by the enter key.
     */
    internal fun waitForInput(): String {
        setInput("")
        waitForEnter()

        return try {
            lock.lock()
            String(input.toCharArray())
        } finally {
            lock.unlock()
        }
    }

    /**
     * Clear the console.
     */
    internal fun clear() {
        // ANSI escape code to clear the screen
        kotlin.io.print("\u001b[H\u001b[2J")
        // flush output
        System.out.flush()
    }

    /**
     * Print a [string] to the console.
     */
    internal fun print(string: String) {
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
    internal fun printlnDirectTemp(string: String, durationInMs: Long = 1000) {
        clear()
        println(string)

        if (durationInMs > 0) {
            Thread.sleep(durationInMs)
        }
    }

    internal companion object {
        /**
         * Get the default width.
         */
        internal const val DEFAULT_WIDTH: Int = 100

        /**
         * Get the default lines.
         */
        internal const val DEFAULT_LINES: Int = 10
    }
}
