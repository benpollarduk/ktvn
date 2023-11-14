package com.github.benpollarduk.ktvn.rendering.frames

import java.awt.Point

/**
 * Provides a text frame that is constrained by character limit.
 */
public class CharacterConstrainedTextFrame private constructor(private val data: Map<Point, Char>) : TextFrame {
    override fun getCharacterPositions(): List<CharacterPosition> {
        val result: MutableList<CharacterPosition> = mutableListOf()
        val rows = data.keys.map { it.y }.distinct().sorted()

        for (row in rows) {
            val entries = data.filter { it.key.y == row }.toSortedMap(compareBy { it.x })
            entries.forEach {
                result.add(CharacterPosition(it.value, it.key.x, it.key.y))
            }
        }

        return result
    }

    public companion object {
        private fun formatAndAddWordToMapOfMeasuredWords(
            word: String,
            isLast: Boolean,
            maxWidthInCharacters: Int,
            measuredWords: MutableList<MeasuredString>
        ) {
            val formattedWord = if (isLast) {
                word
            } else {
                "$word "
            }
            val formattedWordWidth = formattedWord.length

            if (formattedWordWidth > maxWidthInCharacters) {
                var wordPart = ""

                for (characterIndex in formattedWord.indices) {
                    val c = formattedWord[characterIndex]

                    val working = if (characterIndex < formattedWord.length - 1) {
                        "$wordPart$c-"
                    } else {
                        "$wordPart$c"
                    }

                    val workingWidth = working.length

                    if (workingWidth >= maxWidthInCharacters) {
                        measuredWords.add(MeasuredString(working, workingWidth))
                        wordPart = ""
                    } else if (characterIndex == formattedWord.length - 1) {
                        measuredWords.add(MeasuredString(working, workingWidth))
                    } else {
                        wordPart = "$wordPart$c"
                    }
                }
            } else {
                measuredWords.add(MeasuredString(formattedWord, formattedWordWidth))
            }
        }

        /**
         * Break [text] in to a list of [MeasuredString] where the width is expressed in characters.
         */
        internal fun getMeasuredWords(
            text: String,
            maxWidthInPixels: Int
        ): List<MeasuredString> {
            val words = text.split(" ")
            val measuredWords: MutableList<MeasuredString> = mutableListOf()

            // iterate all words and split if they won't fit on a single line
            for (wordIndex in words.indices) {
                formatAndAddWordToMapOfMeasuredWords(
                    words[wordIndex],
                    wordIndex == words.size - 1,
                    maxWidthInPixels,
                    measuredWords
                )
            }

            return measuredWords
        }

        /**
         * Create a list of [CharacterConstrainedTextFrame] from [text] and [parameters]. A collection is required
         * because the text may spill across multiple frames.
         */
        public fun create(text: String, parameters: TextFrameParameters): List<CharacterConstrainedTextFrame> {
            val frames: MutableList<CharacterConstrainedTextFrame> = mutableListOf()
            var currentFrame: MutableMap<Point, Char> = mutableMapOf()
            var currentLineWidth = 0
            var currentColumn = 0
            var currentRow = 0
            val chunks = text.split("\n")

            for (chunk in chunks) {
                for (measuredWord in getMeasuredWords(chunk, parameters.widthConstraint)) {
                    val fitsOnCurrentLine = currentLineWidth + measuredWord.width <= parameters.widthConstraint
                    val startNextLine = currentRow < parameters.availableLines

                    // if the word fits on the current line
                    if (fitsOnCurrentLine) {
                        currentLineWidth += measuredWord.width
                    } else if (startNextLine) {
                        // word goes to next line
                        currentRow++
                        currentColumn = 0
                        currentLineWidth = measuredWord.width
                    } else {
                        // word goes to start of next frame
                        currentRow = 0
                        currentColumn = 0
                        currentLineWidth = measuredWord.width
                        frames.add(CharacterConstrainedTextFrame(currentFrame))
                        currentFrame = mutableMapOf()
                    }

                    measuredWord.string.forEach {
                        currentFrame[Point(currentColumn, currentRow)] = it
                        currentColumn++
                    }
                }

                currentRow++
                currentColumn = 0
                currentLineWidth = 0
            }

            frames.add(CharacterConstrainedTextFrame(currentFrame))
            return frames
        }
    }
}
