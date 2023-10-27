package scenes

import com.github.benpollarduk.ktvn.characters.Character

/**
 * Provides the [position] of a [character].
 */
public data class CharacterPosition(val character: Character, val position: OnscreenPosition)
