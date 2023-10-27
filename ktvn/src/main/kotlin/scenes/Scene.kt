package scenes

import com.github.benpollarduk.ktvn.characters.Character
import scenes.Positions.above
import scenes.Positions.below
import scenes.Positions.center
import scenes.Positions.left
import scenes.Positions.leftOf
import scenes.Positions.right
import scenes.Positions.rightOf

/**
 * Provides a scene.
 */
public class Scene {
    private val mutableList: MutableList<CharacterPosition> = mutableListOf()
    public fun enter(character: Character, from: OffscreenPosition, to: OnscreenPosition) {
        println(from)
        mutableList.removeAll { it.character == character }
        mutableList.add(CharacterPosition(character, to))
    }
    public fun move(character: Character, to: OnscreenPosition) {
        mutableList.removeAll { it.character == character }
        mutableList.add(CharacterPosition(character, to))
    }

    public infix fun moveLeft(character: Character) {
        move(character, left)
    }

    public infix fun moveCenter(character: Character) {
        move(character, center)
    }

    public infix fun moveRight(character: Character) {
        move(character, right)
    }

    public fun exit(character: Character, to: OffscreenPosition) {
        println(to)
        mutableList.removeAll { it.character == character }
    }

    public infix fun exitLeft(character: Character) {
        exit(character, leftOf)
    }

    public infix fun exitTop(character: Character) {
        exit(character, above)
    }

    public infix fun exitRight(character: Character) {
        exit(character, rightOf)
    }

    public infix fun exitDown(character: Character) {
        exit(character, below)
    }
}
