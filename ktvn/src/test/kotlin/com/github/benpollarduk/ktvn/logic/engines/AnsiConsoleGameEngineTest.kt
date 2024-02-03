package com.github.benpollarduk.ktvn.logic.engines

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Narrator
import com.github.benpollarduk.ktvn.logic.configuration.DynamicGameConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class AnsiConsoleGameEngineTest {
    @Test
    fun `Given character speaks then log has one entry`() {
        val engine = AnsiConsoleGameEngine()
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val character = Character("", configuration.gameAdapter.characterAdapter)
        val preSpeak = engine.log.toArray().size

        character.says("Test")
        val postSpeak = engine.log.toArray().size

        Assertions.assertEquals(0, preSpeak)
        Assertions.assertEquals(1, postSpeak)
    }
    @Test
    fun `Given narrator narrates then log has one entry`() {
        val engine = AnsiConsoleGameEngine()
        val configuration = DynamicGameConfiguration()
        configuration.engine = engine
        val narrator = Narrator(configuration.gameAdapter.narratorAdapter)
        val preNarrate = engine.log.toArray().size

        narrator.narrates("Test")
        val postNarrate = engine.log.toArray().size

        Assertions.assertEquals(0, preNarrate)
        Assertions.assertEquals(1, postNarrate)
    }
}