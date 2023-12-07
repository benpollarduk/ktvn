package com.github.benpollarduk.ktvn.logic

import com.github.benpollarduk.ktvn.io.CharacterResourceLookup
import com.github.benpollarduk.ktvn.io.LazyCharacterResourceLookup
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.logic.configuration.GameConfiguration
import com.github.benpollarduk.ktvn.structure.StepIdentifier
import com.github.benpollarduk.ktvn.structure.StepIdentifierMapper
import com.github.benpollarduk.ktvn.structure.Story

/**
 * A visual novel. Contains all the components required to create a [Game] including a [story] and [configuration]. A
 * [desiredResolution] can be specified if a specific resolution should be targeted.
 */
public abstract class VisualNovel(
    public val story: Story,
    public val configuration: GameConfiguration,
    public val desiredResolution: Resolution = Resolution.NOT_SPECIFIED
) {
    private val mapper = StepIdentifierMapper()

    /**
     * Get the [CharacterResourceLookup].
     */
    public open var characterResourceLookup: CharacterResourceLookup = LazyCharacterResourceLookup()

    /**
     * Get the structure of the [story] as an array of [StepIdentifier].
     */
    public var structure: Array<StepIdentifier>
        private set

    init {
        val map = mapper.map(story)

        // assign all steps their identifier
        map.forEach {
            it.key.identifier = it.value
        }

        structure = map.values.toTypedArray()
    }

    public companion object {
        /**
         * Create a new [VisualNovel] with a specified [story] and [configuration].
         */
        public fun create(story: Story, configuration: GameConfiguration): VisualNovel {
            return object : VisualNovel(story, configuration) {
            }
        }
    }
}
