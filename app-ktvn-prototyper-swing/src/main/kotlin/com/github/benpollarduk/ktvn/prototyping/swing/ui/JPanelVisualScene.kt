package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.prototyping.swing.components.VisualScene
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.image.BufferedImage
import javax.swing.JLayeredPane
import javax.swing.JPanel

/**
 * Provides a JPanel that provides a visual representation of a scene.
 */
@Suppress("MagicNumber", "TooManyFunctions")
class JPanelVisualScene(override val sequencedTextArea: JTextPaneSequencedTextArea) : JPanel(), VisualScene {
    private val canvas = SceneCanvasPanel()
    private var backgroundImage: BufferedImage? = null
    private val textAreaMargin: Int = 20
    private val textAreaHeight: Int = 120
    private var _displayTextArea: Boolean = true
    private var _canDisplayTextArea: Boolean = false

    private var canDisplayTextArea: Boolean
        get() = _canDisplayTextArea
        set(value) {
            _canDisplayTextArea = value
            displayTextAreaIfApplicable()
        }

    init {
        canvas.isVisible = false
        canDisplayTextArea = false
        layout = BorderLayout()
        background = Color.WHITE

        val pane = JLayeredPane().also {
            it.add(canvas, JLayeredPane.DEFAULT_LAYER)
            it.add(sequencedTextArea, JLayeredPane.POPUP_LAYER)
        }

        add(pane, BorderLayout.CENTER)

        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                updateScale()
                canvas.setBounds(0, 0, width, height)
                resizeTextArea()
                render()
            }
        })
    }

    private fun resizeTextArea() {
        val topLeft = Point(
            canvas.canvasTopLeft.x + textAreaMargin,
            canvas.canvasTopLeft.y + canvas.canvasSize.height - textAreaMargin - textAreaHeight
        )

        val size = Dimension(
            canvas.canvasSize.width - textAreaMargin - textAreaMargin,
            textAreaHeight
        )

        sequencedTextArea.setBounds(
            topLeft.x,
            topLeft.y,
            size.width,
            size.height
        )

        sequencedTextArea.size = size
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

    private fun displayTextAreaIfApplicable() {
        sequencedTextArea.isVisible = displayTextArea && canDisplayTextArea
        resizeTextArea()
        render()
    }

    override var displayTextArea: Boolean
        get() = _displayTextArea
        set(value) {
            _displayTextArea = value
            displayTextAreaIfApplicable()
        }

    override var desiredResolution: Resolution
        get() = canvas.resolution
        set(value) {
            canvas.resolution = value
            updateScale()
        }

    override fun setBackgroundImage(image: BufferedImage, transition: SceneTransition, listener: () -> Unit) {
        this.backgroundImage = image
        canvas.isVisible = true
        canvas.setBackground(image, transition, listener)
        canDisplayTextArea = true
        resizeTextArea()
        render()
    }

    override fun addCharacter(character: Character, position: Position, image: BufferedImage) {
        canvas.addCharacter(character, position, image)
        render()
    }

    override fun updateCharacter(character: Character, image: BufferedImage) {
        canvas.updateCharacter(character, image)
        render()
    }

    override fun containsCharacter(character: Character): Boolean {
        return canvas.containsCharacter(character)
    }

    override fun moveCharacter(
        character: Character,
        from: Position,
        to: Position,
        transition: LayoutTransition,
        listener: () -> Unit
    ) {
        canvas.moveCharacter(character, to, transition, listener)
    }

    override fun animateCharacter(character: Character, animation: Animation, listener: () -> Unit) {
        canvas.animateCharacter(character, animation, listener)
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
        clearVisuals()
        canvas.isVisible = false
        canDisplayTextArea = false
        render()
    }

    override fun clearVisuals() {
        this.backgroundImage = null
        this.canvas.clear()
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
