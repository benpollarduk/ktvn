package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.Positions
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.layout.XOrigin
import com.github.benpollarduk.ktvn.layout.YOrigin
import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.Point
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.util.concurrent.locks.ReentrantLock
import javax.swing.ImageIcon
import javax.swing.JPanel

/**
 * Provides a panel that can be used as a canvas for rendering scenes.
 */
class SceneCanvasPanel : JPanel() {
    private var backgroundImage: BufferedImage? = null
    private val characterRenders: MutableList<CharacterRender> = mutableListOf()
    private val lock = ReentrantLock()
    private var _scale: Double = 1.0
    private var _resolution: Resolution = Resolution.NOT_SPECIFIED

    /**
     * Get or set the scale.
     */
    var scale: Double
        get() = _scale
        set(value) {
            _scale = value
            reRender()
        }

    /**
     * Get or set the resolution.
     */
    var resolution: Resolution
        get() = _resolution
        set(value) {
            _resolution = value
            reRender()
        }

    init {
        isOpaque = false
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        drawCanvas(g)
    }

    private fun getXPos(xNormalized: Double, xOrigin: XOrigin, backgroundWidth: Int, elementWidth: Int): Int {
        return when (xOrigin) {
            XOrigin.LEFT -> ((backgroundWidth * xNormalized) - (elementWidth / 2)).toInt()
            XOrigin.RIGHT -> (backgroundWidth - (backgroundWidth * xNormalized) - (elementWidth / 2)).toInt()
        }
    }

    private fun getYPos(yNormalized: Double, yOrigin: YOrigin, backgroundHeight: Int, elementHeight: Int): Int {
        return when (yOrigin) {
            YOrigin.TOP -> (elementHeight + (yNormalized * backgroundHeight)).toInt()
            YOrigin.BOTTOM -> (backgroundHeight - (yNormalized * backgroundHeight) - elementHeight).toInt()
        }
    }

    private fun scaleImage(originalImage: Image, scale: Double): BufferedImage {
        val originalWidth = originalImage.getWidth(null)
        val originalHeight = originalImage.getHeight(null)

        val newWidth = (originalWidth * scale).toInt()
        val newHeight = (originalHeight * scale).toInt()

        val bufferedImage = BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB)

        val graphics2D = bufferedImage.createGraphics()
        val transform = AffineTransform()

        transform.scale(scale, scale)

        graphics2D.transform(transform)
        graphics2D.drawImage(originalImage, 0, 0, null)
        graphics2D.dispose()

        return bufferedImage
    }

    private fun drawCanvas(g: Graphics) {
        try {
            lock.lock()

            val bkSize: Dimension
            val bkOffset: Point
            val bk = backgroundImage

            // draw background
            if (bk != null) {
                // use the image, but scale it
                val scaledImage = scaleImage(bk, scale)
                bkSize = Dimension(scaledImage.width, scaledImage.height)
                bkOffset = Point(
                    (width - scaledImage.width) / 2,
                    (height - scaledImage.height) / 2
                )
                g.drawImage(scaledImage, bkOffset.x, bkOffset.y, null)
            } else {
                // no image, use a background color
                bkSize = Dimension(width, height)
                bkOffset = Point(0, 0)
                g.color = background
                g.fillRect(bkOffset.x, bkOffset.y, bkSize.width, bkSize.height)
            }

            // draw characters
            characterRenders.forEach {
                // get scaled image
                val scaledImage = scaleImage(it.image, scale)

                // get positions relative to scaled background
                var x = getXPos(it.position.normalizedX, it.position.xOrigin, bkSize.width, scaledImage.width)
                var y = getYPos(it.position.normalizedY, it.position.yOrigin, bkSize.height, scaledImage.height)

                // apply translation to align with scaled background
                x += bkOffset.x
                y += bkOffset.y

                // draw
                g.drawImage(ImageIcon(scaledImage).image, x, y, null)
            }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Set the background to a specified [image].
     */
    fun setBackground(image: BufferedImage) {
        try {
            lock.lock()
            backgroundImage = image
        } finally {
            lock.unlock()
        }
    }

    /**
     * Add a [character] with a specified [image].
     */
    fun addCharacter(character: Character, image: BufferedImage) {
        try {
            lock.lock()
            val c = characterRenders.firstOrNull { it.character == character }
            if (c != null) {
                characterRenders.removeAll { it.character == character }
                characterRenders.add(CharacterRender(character, image, c.position))
            } else {
                characterRenders.add(CharacterRender(character, image, Positions.none))
            }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Move a [character] to a [position].
     */
    fun moveCharacter(character: Character, position: Position) {
        try {
            lock.lock()

            val c = characterRenders.firstOrNull { it.character == character }
            if (c != null) {
                characterRenders.removeAll { it.character == character }
                characterRenders.add(CharacterRender(character, c.image, position))
            }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Clear the canvas.
     */
    fun clear() {
        backgroundImage = null

        try {
            lock.lock()
            characterRenders.clear()
        } finally {
            lock.unlock()
        }

        reRender()
    }

    private fun reRender() {
        revalidate()
        repaint()
    }
}
