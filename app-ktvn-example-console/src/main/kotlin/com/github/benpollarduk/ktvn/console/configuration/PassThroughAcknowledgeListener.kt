package com.github.benpollarduk.ktvn.console.configuration

import com.github.benpollarduk.ktvn.logic.structure.AcknowledgeListener

/**
 * Provides an object that functions as a listener that allows a simple pass-through.
 */
internal object PassThroughAcknowledgeListener : AcknowledgeListener {
    override fun waitFor() {
        // nothing
    }
}