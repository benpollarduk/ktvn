package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.prototyping.swing.DebugGameEngine
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSlider
import javax.swing.JTextArea
import javax.swing.ScrollPaneConstants
import javax.swing.border.EmptyBorder
import javax.swing.event.ChangeEvent

/**
 * Provides a control for adjusting volumes.
 */
@Suppress("MagicNumber")
class JFrameVolumeControl(engine: DebugGameEngine) : JFrame("Volume") {

    private val masterVolumeSlider = JSlider(JSlider.VERTICAL, 0, 10, 10)
    private val musicVolumeSlider = JSlider(JSlider.VERTICAL, 0, 10, 10)
    private val soundEffectVolumeSlider = JSlider(JSlider.VERTICAL, 0, 10, 10)

    private val masterVolumeLabel = JLabel("Master")
    private val musicVolumeLabel = JLabel("Music")
    private val soundEffectVolumeLabel = JLabel("SFX")

    private val scrollPane = JScrollPane()

    init {
        layout = BorderLayout()
        isResizable = false

        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.X_AXIS)
        panel.border = EmptyBorder(10, 10, 10, 10)

        setSliderProperties(masterVolumeSlider)
        setSliderProperties(musicVolumeSlider)
        setSliderProperties(soundEffectVolumeSlider)

        masterVolumeSlider.value = (engine.volumeManager.getMasterVolume() * 10).toInt()
        musicVolumeSlider.value = (engine.volumeManager.getMusicVolume() * 10).toInt()
        soundEffectVolumeSlider.value = (engine.volumeManager.getSoundEffectVolume() * 10).toInt()

        panel.add(
            createSliderPanel(masterVolumeLabel, masterVolumeSlider) {
                val slider = it.source as JSlider
                val volume = slider.value / 10.0
                engine.volumeManager.setMasterVolume(volume)
                engine.adjustPlayingBackgroundMusicVolume(volume)
            }
        )
        panel.add(
            createSliderPanel(musicVolumeLabel, musicVolumeSlider) {
                val slider = it.source as JSlider
                val volume = slider.value / 10.0
                engine.volumeManager.setMusicVolume(volume)
                engine.adjustPlayingBackgroundMusicVolume(volume)
            }
        )
        panel.add(
            createSliderPanel(soundEffectVolumeLabel, soundEffectVolumeSlider) {
                val slider = it.source as JSlider
                val volume = slider.value / 10.0
                engine.volumeManager.setSoundEffectVolume(volume)
            }
        )

        contentPane.add(panel, BorderLayout.WEST)

        scrollPane.setViewportView(JTextArea())
        scrollPane.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS

        defaultCloseOperation = DISPOSE_ON_CLOSE
        size = Dimension(230, 600)
    }

    private fun setSliderProperties(slider: JSlider) {
        slider.snapToTicks = true
        slider.majorTickSpacing = 1
        slider.paintTicks = true
        slider.paintLabels = true
    }

    private fun createSliderPanel(label: JLabel, slider: JSlider, changeListener: (ChangeEvent) -> Unit): JPanel {
        val sliderPanel = JPanel()
        sliderPanel.layout = BoxLayout(sliderPanel, BoxLayout.Y_AXIS)
        sliderPanel.add(label)
        sliderPanel.add(slider)

        slider.addChangeListener {
            changeListener.invoke(it)
        }

        return sliderPanel
    }
}
