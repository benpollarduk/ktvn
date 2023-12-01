package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.logic.structure.StepIdentifier
import com.github.benpollarduk.ktvn.prototyping.swing.components.Status
import java.awt.FlowLayout
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * Provides a JPanel that provides status information.
 */
class JPanelStatus : JPanel(), Status {
    private val chapterLabel: JLabel = JLabel("Ch:")
    private val sceneLabel: JLabel = JLabel("Sc:")
    private val stepLabel: JLabel = JLabel("St:")
    private val chapterValueLabel: JLabel = JLabel(UNDEFINED_CHAPTER.toString())
    private val sceneValueLabel: JLabel = JLabel(UNDEFINED_SCENE.toString())
    private val stepValueLabel: JLabel = JLabel(UNDEFINED_STEP.toString())

    override fun updatePosition(position: StepIdentifier) {
        chapterValueLabel.text = position.chapter.toString()
        sceneValueLabel.text = position.scene.toString()
        stepValueLabel.text = position.step.toString()
    }

    override fun reset() {
        updatePosition(StepIdentifier(UNDEFINED_CHAPTER, UNDEFINED_SCENE, UNDEFINED_STEP))
    }

    init {
        layout = FlowLayout(FlowLayout.LEFT, MARGIN, MARGIN)

        add(chapterLabel)
        add(chapterValueLabel)
        add(sceneLabel)
        add(sceneLabel)
        add(sceneValueLabel)
        add(stepLabel)
        add(stepValueLabel)
    }

    private companion object {
        /**
         * Get a value representing an undefined chapter.
         */
        private const val UNDEFINED_CHAPTER: Int = 0

        /**
         * Get a value representing an undefined scene.
         */
        private const val UNDEFINED_SCENE: Int = 0

        /**
         * Get a value representing an undefined step.
         */
        private const val UNDEFINED_STEP: Int = 0

        /**
         * Get the margin.
         */
        private const val MARGIN: Int = 5
    }
}
