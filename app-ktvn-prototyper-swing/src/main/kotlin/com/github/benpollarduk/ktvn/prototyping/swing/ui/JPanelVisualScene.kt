package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.characters.Animation
import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.prototyping.swing.components.VisualScene
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import javax.swing.JPanel

/**
 * Provides a JPanel that provides a visual representation of a scene.
 */
@Suppress("MagicNumber")
class JPanelVisualScene : JPanel(), VisualScene {
    private val canvas = SceneCanvasPanel()
    private var backgroundImage: BufferedImage? = null

    init {
        canvas.isVisible = false
        layout = BorderLayout()
        background = Color.WHITE
        add(canvas, BorderLayout.CENTER)

        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                updateScale()
            }
        })
    }

    private fun render() {
        revalidate()
        repaint()
    }

    private fun determineScale(available: Dimension, real: Dimension, allowExpansion: Boolean = true): Double {
        var scaleX = 1.0
        var scaleY = 1.0

        if (real.width > 0 && available.width > 0) {
            scaleX = available.width.toDouble() / real.width.toDouble()
        }

        if (real.height > 0 && available.height > 0) {
            scaleY = available.height.toDouble() / real.height.toDouble()
        }

        return if (allowExpansion) {
            minOf(scaleX, scaleY)
        } else {
            minOf(scaleX, scaleY, 1.0)
        }
    }

    private fun updateScale() {
        canvas.scale = determineScale(
            Dimension(width - MARGIN - MARGIN, height - MARGIN - MARGIN),
            Dimension(desiredResolution.width, desiredResolution.height)
        )
    }

    override var desiredResolution: Resolution
        get() = canvas.resolution
        set(value) {
            canvas.resolution = value
            updateScale()
        }

    override fun setBackgroundImage(image: BufferedImage) {
        this.backgroundImage = image
        canvas.setBackground(image)
        canvas.isVisible = true
        render()
    }

    override fun addOrUpdate(character: Character, image: BufferedImage) {
        canvas.addCharacter(character, image)
        render()
    }

    override fun moveCharacter(character: Character, from: Position, to: Position) {
        canvas.moveCharacter(character, to)
        render()
    }

    override fun animateCharacter(character: Character, animation: Animation) {
        // animate character
    }

    private fun drawCheckerboard(g2d: Graphics2D) {
        val color1 = Color(245, 245, 245)
        val color2 = Color(225, 225, 225)
        val panelWidth = width
        val panelHeight = height

        for (x in 0 until panelWidth step CHECKER_SIZE) {
            for (y in 0 until panelHeight step CHECKER_SIZE) {
                g2d.color = if ((x / CHECKER_SIZE + y / CHECKER_SIZE) % 2 == 0) color1 else color2
                g2d.fillRect(x, y, CHECKER_SIZE, CHECKER_SIZE)
            }
        }
    }

    override fun reset() {
        this.backgroundImage = null
        this.canvas.clear()
        canvas.isVisible = false
        render()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        drawCheckerboard(g2d)
    }

    companion object {
        /**
         * Get the internal margin.
         */
        private const val MARGIN: Int = 20

        /**
         * Get the checker size.
         */
        private const val CHECKER_SIZE: Int = 5
    }
}
