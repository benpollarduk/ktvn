package com.github.benpollarduk.ktvn.swing.ui

import com.github.benpollarduk.ktvn.logic.ProgressionMode
import com.github.benpollarduk.ktvn.swing.DebugGameEngine
import com.github.benpollarduk.ktvn.swing.components.ProgressionControl
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.ItemEvent
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JPanel

@Suppress("MagicNumber")
public class JPanelProgressionControl(private val engine: DebugGameEngine) : JPanel(), ProgressionControl {
    private val progressionLabel: JLabel = JLabel("Progression:")
    private val modeLabel: JLabel = JLabel("Mode:")
    private val forceSkipLabel: JLabel = JLabel("Skip unread?:")
    private val autoDelayLabel: JLabel = JLabel("Auto delay (ms):")

    private val ackButton: JButton = JButton("Acknowledge").also {
        it.addActionListener {
            acknowledge()
        }
    }

    private val modeComboBox: JComboBox<String> = JComboBox(arrayOf(MANUAL, SKIP, AUTO)).also {
        it.preferredSize = Dimension(100, it.preferredSize.height)
        it.addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                setMode(it.selectedItem as String)
            }
        }
    }

    private val forceSkipCheckBox: JCheckBox = JCheckBox().also {
        it.addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED || event.stateChange == ItemEvent.DESELECTED) {
                forceSkip = it.isSelected
                setMode(SKIP)
            }
        }
    }

    private val autoDelayComboBox: JComboBox<String> = JComboBox(arrayOf("0", "1000", "2500", "5000")).also {
        it.preferredSize = Dimension(100, it.preferredSize.height)
        it.addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                autoTimeInMs = (it.selectedItem as String).toLong()
                setMode(AUTO)
            }
        }
    }

    override val mode: ProgressionMode
        get() = engine.progressionController.progressionMode

    override var forceSkip: Boolean = false
    override var autoTimeInMs: Long = 1000

    private fun setMode(mode: String) {
        when (mode) {
            MANUAL -> {
                engine.progressionController.progressionMode = ProgressionMode.WaitForConfirmation
                forceSkipCheckBox.isEnabled = false
                autoDelayComboBox.isEnabled = false
            }
            SKIP -> {
                engine.progressionController.progressionMode = ProgressionMode.Skip(forceSkip)
                forceSkipCheckBox.isEnabled = true
                autoDelayComboBox.isEnabled = false
            }
            AUTO -> {
                engine.progressionController.progressionMode = ProgressionMode.Auto(autoTimeInMs)
                forceSkipCheckBox.isEnabled = false
                autoDelayComboBox.isEnabled = true
            }
        }
    }

    override fun acknowledge() {
        engine.generalAcknowledge()
    }

    override fun jumpTo(chapter: Int, scene: Int, step: Int) {
        TODO("Not yet implemented")
    }

    init {
        layout = FlowLayout(FlowLayout.LEFT, 5, 5)
        preferredSize = Dimension(0, 40)

        add(progressionLabel)
        add(ackButton)
        add(modeLabel)
        add(modeComboBox)
        add(forceSkipLabel)
        add(forceSkipCheckBox)
        add(autoDelayLabel)
        add(autoDelayComboBox)

        setMode("Manual")
    }

    private companion object {
        private const val MANUAL: String = "Manual"
        private const val SKIP: String = "Skip"
        private const val AUTO: String = "Auto"
    }
}
