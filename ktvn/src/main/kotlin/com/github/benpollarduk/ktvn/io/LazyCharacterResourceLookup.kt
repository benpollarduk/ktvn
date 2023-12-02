package com.github.benpollarduk.ktvn.io

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion

/**
 * Provides an interface for a lookup for [Character] resources. A [root] can be specified it the resource default
 * location is not the top level resource directory. An [extension] can be optionally specified for image
 * format (.png is used as default). Unless overridden this will return a resource key for a character in the following
 * format:
 *
 * When a character variation is not specified:     name-emotion.extension
 * When a character variation is specified:         name-variation-emotion.extension
 */
public class LazyCharacterResourceLookup(
    public val root: String = "",
    public val extension: String = ".png"
) : CharacterResourceLookup {
    private val lookup: MutableMap<CharacterLookupState, String> = mutableMapOf()

    private fun createKey(character: Character): CharacterLookupState {
        return createKey(character, character.emotion)
    }

    private fun createKey(character: Character, emotion: Emotion): CharacterLookupState {
        return CharacterLookupState(character.name, character.variation, emotion.toString())
    }

    private fun createResourceKey(character: Character, emotion: Emotion): String {
        val n = character.name.lowercase()
        val v = character.variation.lowercase()
        val e = emotion.toString().lowercase()
        return if (character.variation != "") {
            "$root$n-$v-${e}$extension"
        } else {
            "$root$n-${e}$extension"
        }
    }

    /**
     * Register a resource with a specified [character] and [key]. This will use the characters [emotion].
     */
    public fun registerResource(character: Character, key: String) {
        lookup[createKey(character)] = key
    }

    /**
     * Register a resource with a specified [character], [emotion] and [key].
     */
    public fun registerResource(character: Character, emotion: Emotion, key: String) {
        lookup[createKey(character, emotion)] = key
    }

    /**
     * Register a resource with a specified [characterLookupState].
     */
    public fun registerResource(characterLookupState: CharacterLookupState, key: String) {
        lookup[characterLookupState] = key
    }

    override fun getKey(character: Character): String {
        return getKey(character, character.emotion)
    }

    override fun getKey(character: Character, emotion: Emotion): String {
        return lookup[createKey(character, emotion)] ?: createResourceKey(character, emotion)
    }
}
