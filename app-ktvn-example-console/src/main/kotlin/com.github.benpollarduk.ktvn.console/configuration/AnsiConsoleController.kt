package com.github.benpollarduk.ktvn.console.configuration

import com.github.benpollarduk.ktvn.rendering.sequencing.SequencedTextController
import com.github.benpollarduk.ktvn.rendering.sequencing.SequencedTextDisplayListener
import com.github.benpollarduk.ktvn.rendering.frames.CharacterConstrainedTextFrame
import com.github.benpollarduk.ktvn.rendering.frames.TextFrame
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

    // the text controller is responsible for sequencing and controlling the dispatch of characters from a collection
    // of frames. the listener is used to capture the requested characters and render them on the console
    private val textController = SequencedTextController(TimeBasedTextSequencer {
        // render all the characters in the requested position on the console
        for (position in it) {
            setCursorPosition(position.column + 1, position.row + 1)
            print(position.character)
        }
    })

    init {
        // set up the listener for when the textController has to split a text frame and requires acknowledgement to
        // continue
        textController.addListener(object : SequencedTextDisplayListener {
            override fun startedFrame(frame: TextFrame) {
                // clear the console before rendering the new frame
                clear()
            }

            override fun finishedFrame(frame: TextFrame, acknowledgementRequired: Boolean) {
                // if acknowledgment is required wait for enter to be pressed
                if (acknowledgementRequired)
                    waitForEnter()
            }
        })

        // hide the cursor
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
            textController.acknowledge()

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

        // wait for the input to be received
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
        // reset the input to ensure that legacy input is discarded
        setInput("")
        waitForEnter()

        return try {
            lock.lock()
            // return a clone of the received input
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
        // create frames from the string that describe how it should be rendered
        val frames = CharacterConstrainedTextFrame.create(
            string,
            parameters
        )

        // render the frames on the console
        textController.render(frames)
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
