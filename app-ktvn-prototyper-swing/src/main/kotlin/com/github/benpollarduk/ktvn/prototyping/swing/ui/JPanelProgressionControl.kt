package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.prototyping.swing.DebugGameEngine
import com.github.benpollarduk.ktvn.prototyping.swing.components.ProgressionControl
import com.github.benpollarduk.ktvn.text.sequencing.SequencedTextController
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.ItemEvent
import java.text.NumberFormat
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JFormattedTextField
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.text.NumberFormatter

/**
 * Provides a JPanel that contains progression controls.
 */
@Suppress("MagicNumber")
class JPanelProgressionControl(
    private val engine: DebugGameEngine,
    private val visualScene: JPanelVisualScene,
    private val jumpToListener: (Int, Int, Int) -> Unit
) : JPanel(), ProgressionControl {
    private val modeLabel: JLabel = JLabel("Progression mode:")
    private val speedLabel: JLabel = JLabel("Speed:")
    private val forceSkipLabel: JLabel = JLabel("Skip unread?:")
    private val autoDelayLabel: JLabel = JLabel("Auto delay (ms):")
    private val jumpToLabel: JLabel = JLabel("Jump to:")
    private val chapterLabel: JLabel = JLabel("Ch:")
    private val sceneLabel: JLabel = JLabel("Sc:")
    private val stepLabel: JLabel = JLabel("St:")
    private val showTextAreaLabel: JLabel = JLabel("Show text area?:")

    private val speedComboBox: JComboBox<String> = JComboBox(
        arrayOf(
            DEFAULT_TEXT_SPEED,
            SLOW_TEXT_SPEED,
            FAST_TEXT_SPEED
        )
    ).apply {
        preferredSize = Dimension(70, preferredSize.height)
        addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                setSpeed(selectedItem as String)
            }
        }
    }

    private val ackButton: JButton = JButton("Next").apply {
        isEnabled = false
        preferredSize = Dimension(200, preferredSize.height)
        addActionListener {
            acknowledge()
        }
    }

    private val modeComboBox: JComboBox<String> = JComboBox(
        arrayOf(
            MANUAL_PROGRESSION,
            SKIP_PROGRESSION,
            AUTO_PROGRESSION
        )
    ).apply {
        preferredSize = Dimension(70, preferredSize.height)
        addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                setMode(selectedItem as String)
            }
        }
    }

    private val forceSkipCheckBox: JCheckBox = JCheckBox().apply {
        addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED || event.stateChange == ItemEvent.DESELECTED) {
                forceSkip = isSelected
                setMode(SKIP_PROGRESSION)
            }
        }
    }

    private val showTextAreaCheckBox: JCheckBox = JCheckBox().apply {
        isSelected = visualScene.displayTextArea
        addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED || event.stateChange == ItemEvent.DESELECTED) {
                visualScene.displayTextArea = isSelected
            }
        }
    }

    private val autoDelayComboBox: JComboBox<String> = JComboBox(arrayOf("0", "1000", "2500", "5000")).apply {
        preferredSize = Dimension(60, preferredSize.height)
        selectedIndex = 1
        addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                autoTimeInMs = (selectedItem as String).toLong()
                setMode(AUTO_PROGRESSION)
            }
        }
    }

    private val intFormat = NumberFormat.getInstance()
    private val intFormatter = NumberFormatter(intFormat).apply {
        valueClass = Integer::class.java
        minimum = 0
        maximum = Int.MAX_VALUE
        allowsInvalid = false
    }

    private val chapterIndexTextField = JFormattedTextField(intFormatter).apply {
        text = "1"
        preferredSize = Dimension(30, preferredSize.height)
        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent) {
                SwingUtilities.invokeLater {
                    selectAll()
                }
            }

            override fun focusLost(e: FocusEvent) {
                // no handling
            }
        })
    }

    private val sceneIndexTextField = JFormattedTextField(intFormatter).apply {
        text = "1"
        preferredSize = Dimension(30, preferredSize.height)
        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent) {
                SwingUtilities.invokeLater {
                    selectAll()
                }
            }

            override fun focusLost(e: FocusEvent) {
                // no handling
            }
        })
    }

    private val stepIndexTextField = JFormattedTextField(intFormatter).apply {
        text = "1"
        preferredSize = Dimension(30, preferredSize.height)
        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent) {
                SwingUtilities.invokeLater {
                    selectAll()
                }
            }

            override fun focusLost(e: FocusEvent) {
                // no handling
            }
        })
    }

    private val jumpToButton: JButton = JButton("Jump").apply {
        addActionListener {
            val chapterIndex = chapterIndexTextField.text.toInt()
            val sceneIndex = sceneIndexTextField.text.toInt()
            val stepIndex = stepIndexTextField.text.toInt()
            jumpToListener(chapterIndex, sceneIndex, stepIndex)
        }
    }

    override val mode: ProgressionMode
        get() = engine.progressionController.progressionMode

    override var forceSkip: Boolean = false
    override var autoTimeInMs: Long = 1000

    private fun setMode(mode: String) {
        when (mode) {
            MANUAL_PROGRESSION -> {
                engine.progressionController.progressionMode = ProgressionMode.WaitForConfirmation
                forceSkipLabel.isEnabled = false
                forceSkipCheckBox.isEnabled = false
                autoDelayLabel.isEnabled = false
                autoDelayComboBox.isEnabled = false
            }
            SKIP_PROGRESSION -> {
                engine.progressionController.progressionMode = ProgressionMode.Skip(forceSkip)
                forceSkipLabel.isEnabled = true
                forceSkipCheckBox.isEnabled = true
                autoDelayLabel.isEnabled = false
                autoDelayComboBox.isEnabled = false
            }
            AUTO_PROGRESSION -> {
                engine.progressionController.progressionMode = ProgressionMode.Auto(autoTimeInMs)
                forceSkipLabel.isEnabled = false
                forceSkipCheckBox.isEnabled = false
                autoDelayLabel.isEnabled = true
                autoDelayComboBox.isEnabled = true
            }
        }
    }

    private fun setSpeed(speed: String) {
        when (speed) {
            DEFAULT_TEXT_SPEED -> {
                engine.textController.msBetweenCharacters = DEFAULT_SPEED_IN_MS
            }
            FAST_TEXT_SPEED -> {
                engine.textController.msBetweenCharacters = FAST_SPEED_IN_MS
            }
            SLOW_TEXT_SPEED -> {
                engine.textController.msBetweenCharacters = SLOW_SPEED_IN_MS
            }
        }
    }

    override fun acknowledge() {
        engine.generalAcknowledge()
    }

    override fun allowAcknowledge(allow: Boolean) {
        ackButton.isEnabled = allow
    }

    override fun reset() {
        modeComboBox.selectedItem = MANUAL_PROGRESSION
        speedComboBox.selectedItem = DEFAULT_TEXT_SPEED
    }

    init {
        layout = FlowLayout(FlowLayout.LEFT, 5, 5)

        add(modeLabel)
        add(modeComboBox)
        add(speedLabel)
        add(speedComboBox)
        add(forceSkipLabel)
        add(forceSkipCheckBox)
        add(autoDelayLabel)
        add(autoDelayComboBox)
        add(jumpToLabel)
        add(chapterLabel)
        add(chapterIndexTextField)
        add(sceneLabel)
        add(sceneIndexTextField)
        add(stepLabel)
        add(stepIndexTextField)
        add(jumpToButton)
        add(ackButton)
        add(showTextAreaLabel)
        add(showTextAreaCheckBox)

        setMode(MANUAL_PROGRESSION)
    }

    private companion object {
        /**
         * Get a string representing manual progression.
         */
        private const val MANUAL_PROGRESSION: String = "Manual"

        /**
         * Get a string representing skip progression.
         */
        private const val SKIP_PROGRESSION: String = "Skip"

        /**
         * Get a string representing auto progression.
         */
        private const val AUTO_PROGRESSION: String = "Auto"

        /**
         * Get a string representing default text speed.
         */
        private const val DEFAULT_TEXT_SPEED: String = "Default"

        /**
         * Get a string representing slow text speed.
         */
        private const val SLOW_TEXT_SPEED: String = "Slow"

        /**
         * Get a string representing fast text speed.
         */
        private const val FAST_TEXT_SPEED: String = "Fast"

        /**
         * Get the default text speed in milliseconds.
         */
        private const val DEFAULT_SPEED_IN_MS: Long = SequencedTextController.DEFAULT_MS_BETWEEN_CHARACTERS

        /**
         * Get the slow text speed in milliseconds.
         */
        private const val SLOW_SPEED_IN_MS: Long = 100

        /**
         * Get the fast text speed in milliseconds.
         */
        private const val FAST_SPEED_IN_MS: Long = 10
    }
}
