package com.github.benpollarduk.ktvn.prototyping.swing.ui

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.animations.Animation
import com.github.benpollarduk.ktvn.characters.animations.Laugh
import com.github.benpollarduk.ktvn.characters.animations.Shake
import com.github.benpollarduk.ktvn.layout.Position
import com.github.benpollarduk.ktvn.layout.PositionTranslator
import com.github.benpollarduk.ktvn.layout.Positions
import com.github.benpollarduk.ktvn.layout.Resolution
import com.github.benpollarduk.ktvn.layout.transitions.FadeIn
import com.github.benpollarduk.ktvn.layout.transitions.FadeOut
import com.github.benpollarduk.ktvn.layout.transitions.Instant
import com.github.benpollarduk.ktvn.layout.transitions.LayoutTransition
import com.github.benpollarduk.ktvn.layout.transitions.Slide
import com.github.benpollarduk.ktvn.prototyping.swing.CharacterRender
import com.github.benpollarduk.ktvn.prototyping.swing.ImageResolver
import com.github.benpollarduk.ktvn.prototyping.swing.animations.character.LaughAnimation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.character.ShakeAnimation
import com.github.benpollarduk.ktvn.prototyping.swing.animations.layout.FadeInCharacter
import com.github.benpollarduk.ktvn.prototyping.swing.animations.layout.FadeOutCharacter
import com.github.benpollarduk.ktvn.prototyping.swing.animations.layout.InstantCharacter
import com.github.benpollarduk.ktvn.prototyping.swing.animations.layout.SlideCharacter
import com.github.benpollarduk.ktvn.prototyping.swing.animations.scene.FadeInBackground
import com.github.benpollarduk.ktvn.prototyping.swing.animations.scene.FadeOutBackground
import com.github.benpollarduk.ktvn.prototyping.swing.animations.scene.InstantBackground
import com.github.benpollarduk.ktvn.structure.transitions.SceneTransition
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Point
import java.awt.image.BufferedImage
import java.util.concurrent.locks.ReentrantLock
import javax.swing.ImageIcon
import javax.swing.JPanel

/**
 * Provides a panel that can be used as a canvas for rendering scenes.
 */
@Suppress("MagicNumber", "TooManyFunctions")
class SceneCanvasPanel(
    private val backgroundColor: Color = Color.BLACK,
    private val renderBackground: Boolean = true,
    private val clipToBackground: Boolean = true
) : JPanel() {
    private var backgroundImage: BufferedImage? = null
    private val characterRenders: MutableList<CharacterRender> = mutableListOf()
    private val lock = ReentrantLock()
    private var _scale: Double = 1.0
    private var _resolution: Resolution = Resolution.NOT_SPECIFIED

    /**
     * Get the background fade opacity.
     */
    var backgroundFadeOpacity = 1.0

    /**
     * Get or set the color to use for background fades.
     */
    var backgroundFadeColor: Color = Color.BLACK

    /**
     * Get the canvas top left position.
     */
    var canvasTopLeft: Point = Point(0, 0)
        private set

    /**
     * Get the canvas size.
     */
    var canvasSize: Dimension = Dimension(0, 0)
        private set

    /**
     * Get or set the scale.
     */
    var scale: Double
        get() = _scale
        set(value) {
            _scale = value
            recalculateCanvasDimensions()
            reRender()
        }

    /**
     * Get or set the resolution.
     */
    var resolution: Resolution
        get() = _resolution
        set(value) {
            _resolution = value
            recalculateCanvasDimensions()
            reRender()
        }

    init {
        isOpaque = false
    }

    private fun recalculateCanvasDimensions() {
        canvasSize = Dimension((resolution.width * scale).toInt(), (resolution.height * scale).toInt())
        canvasTopLeft = Point((width - canvasSize.width) / 2, (height - canvasSize.height) / 2)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        drawCanvas(g)
    }

    private fun drawCanvas(g: Graphics) {
        try {
            lock.lock()

            val bk = backgroundImage

            // get dimensions
            val scaledImage: BufferedImage? = if (bk != null) {
                // use the image, but scale it
                ImageResolver.scaleImage(bk, scale)
            } else if (resolution.width * resolution.height > 0) {
                // use resolution
                null
            } else {
                // use canvas size
                null
            }

            // draw background if required
            if (renderBackground) {
                g.color = backgroundColor
                g.fillRect(canvasTopLeft.x, canvasTopLeft.y, canvasSize.width, canvasSize.height)
            }

            // draw image
            if (scaledImage != null) {
                g.drawImage(scaledImage, canvasTopLeft.x, canvasTopLeft.y, null)
            }

            // save the current clipping area
            val clip = g.clip

            // clip to background if required, otherwise characters can overhang*
            if (clipToBackground) {
                // set the clipping area to the intersection of the character and background
                g.clipRect(canvasTopLeft.x, canvasTopLeft.y, canvasSize.width, canvasSize.height)
            }

            // draw characters
            characterRenders.forEach {
                // get scaled image
                val characterImage = getCharacterImage(it.image, it.opacity, scale)

                // get positions relative to scaled background
                val point = PositionTranslator.topLeftOfObjectInArea(
                    canvasSize,
                    Dimension(characterImage.width, characterImage.height),
                    it.currentX,
                    it.currentY
                )

                // apply translation to align with scaled background
                point.x += canvasTopLeft.x
                point.y += canvasTopLeft.y

                // draw
                g.drawImage(ImageIcon(characterImage).image, point.x, point.y, null)
            }

            // restore the original clipping area
            g.clip = clip

            // draw fade if required
            if (backgroundFadeOpacity < 1.0) {
                g.color = Color(
                    backgroundFadeColor.red,
                    backgroundFadeColor.green,
                    backgroundFadeColor.blue,
                    (255 * (1.0 - backgroundFadeOpacity)).toInt()
                )
                g.fillRect(canvasTopLeft.x, canvasTopLeft.y, canvasSize.width, canvasSize.height)
            }
        } finally {
            lock.unlock()
        }
    }

    private fun getCharacterImage(image: BufferedImage, opacity: Double, scale: Double): BufferedImage {
        val scaled = ImageResolver.scaleImage(image, scale)
        val result = BufferedImage(scaled.width, scaled.height, BufferedImage.TYPE_INT_ARGB)
        val g2d = result.createGraphics()

        try {
            // draw the image with the specified opacity
            g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity.toFloat())
            g2d.drawImage(scaled, 0, 0, null)
        } finally {
            g2d.dispose()
        }

        return result
    }

    private fun getCharacterCurrentPosition(character: Character): Position {
        try {
            lock.lock()
            return characterRenders.firstOrNull { it.character == character }?.position ?: Positions.leftOf
        } finally {
            lock.unlock()
        }
    }

    /**
     * Re-render the canvas.
     */
    fun reRender() {
        revalidate()
        repaint()
    }

    /**
     * Get if this contains a [character].
     */
    fun containsCharacter(character: Character): Boolean {
        try {
            lock.lock()
            return characterRenders.any { it.character == character }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Update a [character] with a specified [image].
     */
    fun updateCharacter(character: Character, image: BufferedImage) {
        try {
            lock.lock()
            val c = characterRenders.firstOrNull { it.character == character }
            if (c != null) {
                c.image = image
            }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Add a [character] with a specified [position] and [image].
     */
    fun addCharacter(character: Character, position: Position, image: BufferedImage) {
        try {
            lock.lock()
            val c = characterRenders.firstOrNull { it.character == character }
            if (c == null) {
                characterRenders.add(
                    CharacterRender(
                        character,
                        image,
                        position,
                        position.normalizedX,
                        position.normalizedY
                    )
                )
            }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Set the background to a specified [image]. The [listener] is invoked when the transition is complete.
     */
    fun setBackground(image: BufferedImage, transition: SceneTransition, listener: () -> Unit) {
        try {
            lock.lock()
            backgroundImage = image
        } finally {
            lock.unlock()
        }

        val animation = when (transition) {
            is com.github.benpollarduk.ktvn.structure.transitions.FadeIn -> {
                FadeInBackground(this, transition)
            }
            is com.github.benpollarduk.ktvn.structure.transitions.FadeOut -> {
                FadeOutBackground(this, transition)
            }
            is com.github.benpollarduk.ktvn.structure.transitions.Instant -> {
                InstantBackground(this)
            }
            else -> {
                null
            }
        }

        animation?.invoke(listener) ?: listener.invoke()
    }

    /**
     * Move a [character] to a [position] with a [transition]. The [listener] is invoked when the transition is
     * complete.
     */
    fun moveCharacter(character: Character, position: Position, transition: LayoutTransition, listener: () -> Unit) {
        val render: CharacterRender?
        try {
            lock.lock()
            render = characterRenders.firstOrNull { it.character == character }
        } finally {
            lock.unlock()
        }

        if (render != null) {
            val animation = when (transition) {
                is Slide -> {
                    val startPos = getCharacterCurrentPosition(character)
                    SlideCharacter(this, render, startPos, position, transition)
                }
                is FadeIn -> {
                    FadeInCharacter(this, render, position, 0.0, 1.0, transition)
                }
                is FadeOut -> {
                    FadeOutCharacter(this, render, position, 1.0, 0.0, transition)
                }
                is Instant -> {
                    InstantCharacter(this, render, position)
                }
                else -> {
                    null
                }
            }

            animation?.invoke(listener) ?: listener.invoke()
        } else {
            listener.invoke()
        }
    }

    /**
     * Animate a [character] with a specified [animation]. The [listener] is invoked when the transition is
     * complete.
     */
    fun animateCharacter(character: Character, animation: Animation, listener: () -> Unit) {
        val render: CharacterRender?
        try {
            lock.lock()
            render = characterRenders.firstOrNull { it.character == character }
        } finally {
            lock.unlock()
        }

        if (render != null) {
            render.animationController?.stop()
            val anim = when (animation) {
                is Laugh -> {
                    LaughAnimation(this, render, animation)
                }
                is Shake -> {
                    ShakeAnimation(this, render, animation)
                }
                else -> {
                    null
                }
            }

            anim?.invoke(listener) ?: listener.invoke()
        } else {
            listener.invoke()
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
}
