package com.github.benpollarduk.ktvn.rendering.text.frames

import java.awt.Font
import java.awt.FontMetrics
import java.awt.Point
import java.awt.image.BufferedImage

/**
 * Provides a standard frame of text.
 */
public class StandardTextFrame private constructor(private val data: Map<Point, Char>) : TextFrame {
    override fun getCharacterPositions(): List<CharacterPosition> {
        val result: MutableList<CharacterPosition> = mutableListOf()
        val rows = data.keys.map { it.x }.distinct().sorted()

        for (row in rows) {
            val entries = data.filter { it.key.x == row }
            entries.forEach {
                result.add(CharacterPosition(it.value, it.key.x, it.key.y))
            }
        }

        return result
    }

    public companion object {
        /**
         * Break [text] in to a map where the key is a [String] and the value an [Int] representing the words width, in
         * pixels.
         */
        public fun getMeasuredWords(
            text: String,
            maxWidthInPixels: Int,
            fontMetrics: FontMetrics
        ) : Map<String, Int> {
            val words = text.split(" ")
            val measuredWords: MutableMap<String, Int> = mutableMapOf()

            // iterate all words and split if they won't fit on a single line
            for (wordIndex in words.indices) {
                val word = words[wordIndex]
                val formattedWord = if (wordIndex < words.size - 1) {
                    "$word "
                } else {
                    word
                }
                val formattedWordWidth = fontMetrics.stringWidth(formattedWord)

                if (formattedWordWidth > maxWidthInPixels) {
                    var wordPart = ""

                    for (characterIndex in formattedWord.indices) {
                        val c = formattedWord[characterIndex]

                        val working = if (characterIndex < formattedWord.length - 1) {
                            "$wordPart$c-"
                        } else {
                            "$wordPart$c"
                        }

                        val workingWidth = fontMetrics.stringWidth(working)

                        if (workingWidth > maxWidthInPixels) {
                            measuredWords[working] = workingWidth
                            wordPart = ""
                        } else if (characterIndex == formattedWord.length - 1) {
                            measuredWords[working] = workingWidth
                        } else {
                            wordPart = "$wordPart$c"
                        }
                    }
                } else {
                    measuredWords[formattedWord] = formattedWordWidth
                }
            }

            return measuredWords
        }

        /**
         * Create font metrics for a specified [font].
         */
        public fun createFontMetrics(font: Font) : FontMetrics {
            val bufferedImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
            val graphics = bufferedImage.createGraphics()
            return graphics.getFontMetrics(font)
        }

        /**
         * Create an array of [StandardTextFrame] from [text] and [parameters]. A collection is required because the
         * text may spill across multiple frames.
         */
        public fun create(text: String, parameters: TextAreaParameters) : List<StandardTextFrame> {
            val fontMetrics = createFontMetrics(parameters.font)
            val frames: MutableList<StandardTextFrame> = mutableListOf()
            var currentFrame: MutableMap<Point, Char> = mutableMapOf()
            var currentLineWidth = 0
            var currentColumn = 0
            var currentRow = 0
            val chunks = text.split("\n")

            for (chunk in chunks) {
                for (measuredWord in getMeasuredWords(chunk, parameters.widthInPixels, fontMetrics)) {
                    val fitsOnCurrentLine = currentLineWidth + measuredWord.value <= parameters.widthInPixels
                    val startNextLine = currentRow < parameters.availableLines

                    // if the word fits on the current line
                    if (fitsOnCurrentLine) {
                        currentLineWidth += measuredWord.value
                    }
                    else if (startNextLine) {
                        // word goes to next line
                        currentRow++
                        currentColumn = 0
                        currentLineWidth = measuredWord.value
                    } else {
                        // word goes to start of next frame
                        currentRow = 0
                        currentColumn = 0
                        currentLineWidth = measuredWord.value
                        frames.add(StandardTextFrame(currentFrame))
                        currentFrame = mutableMapOf()
                    }

                    measuredWord.key.forEach {
                        currentFrame[Point(currentColumn, currentRow)] = it
                        currentColumn++
                    }
                }

                currentColumn = 0
                currentLineWidth = 0
            }

            frames.add(StandardTextFrame(currentFrame))
            return frames
        }
    }
}
