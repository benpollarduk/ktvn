package com.github.benpollarduk.ktvn.text.frames

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.awt.Font

class SizeConstrainedTextFrameTest {
    @Test
    fun `given text that fits on 1 line when create then 1 frame returned`() {
        // Given
        val text = "Here is some text."
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(300, 4, font)

        // When
        val result = SizeConstrainedTextFrame.create(text, parameters)

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given text that fits on 2 lines when create then 1 frame with 2 lines returned`() {
        // Given
        val text = "Here is some text."
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(80, 4, font)

        // When
        val frames = SizeConstrainedTextFrame.create(text, parameters)
        val positions = frames.first().getCharacterPositions()
        val result = positions.map { it.row }.distinct().count()

        // Then
        Assertions.assertEquals(1, frames.size)
        Assertions.assertEquals(2, result)
    }

    @Test
    fun `given text that is on 3 lines when create then 1 frame with 3 lines returned`() {
        // Given
        val text = "Here is a question?\n -1: Answer 1\n -2 Answer 2"
        val parameters = TextFrameParameters(300, 4)

        // When
        val frames = SizeConstrainedTextFrame.create(text, parameters)
        val positions = frames.first().getCharacterPositions()
        val result = positions.map { it.row }.distinct().count()

        // Then
        Assertions.assertEquals(1, frames.size)
        Assertions.assertEquals(3, result)
    }

    @Test
    fun `given text that is split over 2 lines when create then 1 frame returned`() {
        // Given
        val text = "Here is some text.\nHere is some text."
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(300, 4, font)

        // When
        val result = SizeConstrainedTextFrame.create(text, parameters)

        // Then
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun `given text that is split over 2 lines and fits on 4 lines when create then 1 frame with 4 lines returned`() {
        // Given
        val text = "HELLO TEST\nHELLO TEST"
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(60, 4, font)

        // When
        val frames = SizeConstrainedTextFrame.create(text, parameters)
        val positions = frames.first().getCharacterPositions()
        val result = positions.map { it.row }.distinct().count()

        // Then
        Assertions.assertEquals(1, frames.size)
        Assertions.assertEquals(4, result)
    }

    @Test
    fun `given text spills to 2 frames when create then 2 frames returned`() {
        // Given
        val text = "A big grumpy dog sat on a big grumpy mate, and then it said boo!"
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(60, 4, font)

        // When
        val frames = SizeConstrainedTextFrame.create(text, parameters)
        val result = frames.size

        // Then
        Assertions.assertEquals(2, result)
    }

    @Test
    fun `given single word that fits on one line when get measured words then 1 element returned`() {
        // Given
        val text = "ABCDEFGHIJKLMN"
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(300, 4, font)

        // When
        val fontMetrics = SizeConstrainedTextFrame.createFontMetrics(parameters.font)
        val words = SizeConstrainedTextFrame.getMeasuredWords(text, parameters.widthConstraint, fontMetrics)
        val result = words.size

        // Then
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `given single word that spills to two lines when get measured words then 2 element returned`() {
        // Given
        val text = "ABCDEFGHIJKLMN"
        val font = Font("Arial", Font.PLAIN, 12)
        val parameters = TextFrameParameters(60, 4, font)

        // When
        val fontMetrics = SizeConstrainedTextFrame.createFontMetrics(parameters.font)
        val words = SizeConstrainedTextFrame.getMeasuredWords(text, parameters.widthConstraint, fontMetrics)
        val result = words.size

        // Then
        Assertions.assertEquals(2, result)
    }
}
