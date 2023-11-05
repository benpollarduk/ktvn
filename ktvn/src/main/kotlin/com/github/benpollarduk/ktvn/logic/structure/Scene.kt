package com.github.benpollarduk.ktvn.logic.structure

import com.github.benpollarduk.ktvn.backgrounds.Background
import com.github.benpollarduk.ktvn.backgrounds.EmptyBackground
import com.github.benpollarduk.ktvn.layout.Layout
import com.github.benpollarduk.ktvn.layout.Layout.Companion.createLayout
import com.github.benpollarduk.ktvn.logic.Flags

/**
 * Provides a scene with a specified [setup].
 */
public class Scene private constructor(setup: (Scene) -> Unit) {
    private var content: List<Step> = emptyList()

    /**
     * Get the background for this [Scene].
     */
    public var background: Background = EmptyBackground()
        private set

    /**
     * Get the layout for this [Scene].
     */
    public var layout: Layout = createLayout { }
        private set

    /**
     * Get the type of [Scene] this is.
     */
    public var type: SceneType = SceneType.Dialog
        private set

    /**
     * Get the index of the current [Step].
     */
    public var indexOfCurrentStep: Int = 0
        private set

    /**
     * Get the name of this [Scene].
     */
    public var name: String = ""
        private set

    init {
        setup(this)
    }

    /**
     * Set the [background].
     */
    public infix fun background(background: Background) {
        this.background = background
    }

    /**
     * Set the [steps] that make up this scene.
     */
    public infix fun steps(steps: List<Step>) {
        this.content = steps
    }

    /**
     * Set the [type] of scene this is.
     */
    public infix fun type(type: SceneType) {
        this.type = type
    }

    /**
     * Set the [layout] for this scene.
     */
    public infix fun layout(layout: Layout) {
        this.layout = layout
    }

    /**
     * Set the [name] for this scene.
     */
    public infix fun name(name: String) {
        this.name = name
    }

    /**
     * Begin the scene with specified [flags] from a specified [startStep].
     */
    @Suppress("ReturnCount")
    internal fun begin(flags: Flags, startStep: Int = 0): SceneResult {
        var indexOfCurrentStep = startStep

        while (indexOfCurrentStep < content.size) {
            when (val result = content[indexOfCurrentStep](flags)) {
                is StepResult.Continue -> { indexOfCurrentStep++ }
                is StepResult.GotoStep -> {
                    indexOfCurrentStep = content.indexOfFirst { it.name.equals(result.name, true) }
                }
                is StepResult.SelectChapter -> {
                    return SceneResult.SelectChapter(result.name)
                }
                is StepResult.SelectScene -> {
                    return SceneResult.SelectScene(result.name)
                }
                is StepResult.End -> {
                    return SceneResult.End(result.ending)
                }
            }
        }

        return SceneResult.Continue
    }

    public companion object {
        /**
         * Create a [Scene] with a specified [setup].
         */
        public infix fun scene(setup: (Scene) -> Unit): Scene {
            return Scene(setup)
        }
    }
}
