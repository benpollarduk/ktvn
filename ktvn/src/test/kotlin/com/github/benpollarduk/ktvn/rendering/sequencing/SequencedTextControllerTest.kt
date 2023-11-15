package com.github.benpollarduk.ktvn.rendering.sequencing

import com.github.benpollarduk.ktvn.logic.structure.CancellationToken
import com.github.benpollarduk.ktvn.rendering.frames.SizeConstrainedTextFrame
import com.github.benpollarduk.ktvn.rendering.frames.TextFrame
import com.github.benpollarduk.ktvn.rendering.frames.TextFrameParameters
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.awt.Font

@Suppress("MaxLineLength")
class SequencedTextControllerTest {
    @Test
    fun `given a line of text that spans multiple frames when rendering then acknowledgement required changed is called`() {
        // Given
        val text = "This is just line of text that will hopefully render over a few frames and trigger an acknowledgment."
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(100, 4, font)
        val frames = SizeConstrainedTextFrame.create(text, parameters)
        val sequencer = TimeBasedTextSequencer(1) {
            for (position in it) {
                print(position.character)
            }
        }
        var called = false
        val display = SequencedTextController(sequencer)
        val listener: SequencedTextDisplayListener = object : SequencedTextDisplayListener {
            override fun startedFrame(frame: TextFrame) {
                // nothing
            }

            override fun finishedFrame(frame: TextFrame, acknowledgementRequired: Boolean) {
                called = true
                display.acknowledge()
            }
        }
        display.addListener(listener)

        // When
        display.render(frames, CancellationToken())

        // Then
        Assertions.assertTrue(called)
    }
}
