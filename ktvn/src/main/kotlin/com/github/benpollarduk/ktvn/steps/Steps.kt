package com.github.benpollarduk.ktvn.steps

public fun steps(vararg steps: Step): List<Step> {
    var mutableSteps: MutableList<Step> = mutableListOf()
    steps.forEach { mutableSteps.add(it) }
    return mutableSteps.toList()
}
