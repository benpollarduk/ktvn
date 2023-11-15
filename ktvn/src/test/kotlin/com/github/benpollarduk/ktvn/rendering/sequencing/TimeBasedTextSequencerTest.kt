package com.github.benpollarduk.ktvn.rendering.sequencing

import com.github.benpollarduk.ktvn.rendering.frames.SizeConstrainedTextFrame
import com.github.benpollarduk.ktvn.rendering.frames.TextFrame
import com.github.benpollarduk.ktvn.rendering.frames.TextFrameParameters
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.awt.Font

class TimeBasedTextSequencerTest {
    @Test
    fun `given a string with 5 characters when sequence then listener called 5 times`() {
        // Given
        val text = "12345"
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(300, 4, font)
        val frame: TextFrame = SizeConstrainedTextFrame.create(text, parameters).first()
        var count = 0
        val sequencer = TimeBasedTextSequencer(1) {
            count++
        }

        // When
        sequencer.sequence(frame)

        // Then
        Assertions.assertEquals(5, count)
    }

    @Test
    fun `given a string with 10 characters when sequence then listener called 10 times`() {
        // Given
        val text = "0123456789"
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(300, 4, font)
        val frame: TextFrame = SizeConstrainedTextFrame.create(text, parameters).first()
        var count = 0
        val sequencer = TimeBasedTextSequencer(1) {
            count++
        }

        // When
        sequencer.sequence(frame)

        // Then
        Assertions.assertEquals(10, count)
    }
}
