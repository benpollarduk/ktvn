package com.github.benpollarduk.ktvn.console

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Speaks
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.characters.Emotes
import com.github.benpollarduk.ktvn.characters.Narrates
import com.github.benpollarduk.ktvn.example.ExampleScript
import org.apache.logging.log4j.kotlin.Logging

object Main : Logging {
    @JvmStatic
    fun main(args: Array<String>) {
        val speaks = object : Speaks {
            override fun invoke(character: Character, line: String) {
                println("${character.name}: $line")
                readln()
            }
        }

        val emotionChanged = object : Emotes {
            override fun invoke(character: Character, emotion: Emotion) {
                println("${character.name} looks $emotion.")
                readln()
            }
        }

        val narrates = object : Narrates {
            override fun invoke(line: String) {
                println(line)
                readln()
            }
        }

        logger.info("Beginning execution of example...")
        val example = ExampleScript(speaks, emotionChanged, narrates)
        example.begin()
        logger.info("Ended execution of example.")
    }
}
