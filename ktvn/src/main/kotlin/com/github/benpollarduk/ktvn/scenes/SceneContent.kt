package com.github.benpollarduk.ktvn.scenes

/**
 * A series of [Step] that make up the content of a [Scene].
 */
public typealias SceneContent = List<Step>

/**
 * Convert one or ore [steps] in to [SceneContent].
 */
public fun content(vararg steps: Step): SceneContent {
    var mutableSteps: MutableList<Step> = mutableListOf()
    steps.forEach { mutableSteps.add(it) }
    return mutableSteps.toList()
}
