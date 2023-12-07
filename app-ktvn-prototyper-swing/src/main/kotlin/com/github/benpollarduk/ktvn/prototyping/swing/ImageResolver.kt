package com.github.benpollarduk.ktvn.prototyping.swing

import com.github.benpollarduk.ktvn.characters.Character
import com.github.benpollarduk.ktvn.characters.Emotion
import com.github.benpollarduk.ktvn.io.CharacterResourceLookup
import com.github.benpollarduk.ktvn.prototyping.swing.components.EventTerminal
import com.github.benpollarduk.ktvn.prototyping.swing.components.ResourceTracker
import com.github.benpollarduk.ktvn.prototyping.swing.components.VisualScene
import java.awt.Color
import java.awt.Image
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Provides a class to help with resolving images.
 */
@Suppress("MagicNumber")
class ImageResolver(
    private val characterResourceLookup: CharacterResourceLookup,
    private val eventTerminal: EventTerminal,
    private val resourceTracker: ResourceTracker,
    private val visualScene: VisualScene
) {
    /**
     * Get an image of a [character] showing an [emotion]. [storyLocation] is important for logging purposes.
     */
    fun getCharacterImage(character: Character, emotion: Emotion, storyLocation: String): BufferedImage {
        val key = characterResourceLookup.getKey(character, emotion)
        val characterImage = getCharacterFromResource(key, storyLocation)

        if (characterImage != null) {
            eventTerminal.println(Severity.INFO, "$key found.")
        } else {
            eventTerminal.println(Severity.ERROR, "$key not found.")
        }

        return characterImage ?: getMissingCharacterResourceImage()
    }

    /**
     * Get a background image from a specified [path]. [storyLocation] is important for logging purposes.
     */
    fun getBackgroundFromFile(path: String, storyLocation: String): BufferedImage? {
        val imageFile = File(path)

        return if (imageFile.exists()) {
            resourceTracker.registerResourceLocated(path, storyLocation, ResourceType.BACKGROUND)
            ImageIO.read(imageFile)
        } else {
            resourceTracker.registerResourceLocated(path, storyLocation, ResourceType.BACKGROUND)
            eventTerminal.println(Severity.ERROR, "File not found: $path.")
            null
        }
    }

    /**
     * Get a background image from a specified [key]. [storyLocation] is important for logging purposes.
     */
    fun getBackgroundFromResource(key: String, storyLocation: String): BufferedImage? {
        val inputStream = javaClass.classLoader.getResourceAsStream(key)
        return if (inputStream != null) {
            resourceTracker.registerResourceLocated(key, storyLocation, ResourceType.BACKGROUND)
            ImageIO.read(inputStream)
        } else {
            resourceTracker.registerResourceMissing(key, storyLocation, ResourceType.BACKGROUND)
            eventTerminal.println(Severity.ERROR, "Resource not found: $key.")
            null
        }
    }

    /**
     * Get a background from a [color].
     */
    fun getBackgroundFromColor(color: Color): BufferedImage {
        return getImageFromColor(color, visualScene.desiredResolution.width, visualScene.desiredResolution.height)
    }

    /**
     * Get a missing character resource.
     */
    private fun getMissingCharacterResourceImage(): BufferedImage {
        return getMissingCharacterResourceImage(
            Color.BLUE,
            visualScene.desiredResolution.width / 4,
            (visualScene.desiredResolution.height * 0.75).toInt()
        )
    }

    /**
     * Get a character from a resource with a given [key] and [storyLocation].
     */
    private fun getCharacterFromResource(key: String, storyLocation: String): BufferedImage? {
        val inputStream = javaClass.classLoader.getResourceAsStream(key)
        return if (inputStream != null) {
            resourceTracker.registerResourceLocated(key, storyLocation, ResourceType.CHARACTER)
            ImageIO.read(inputStream)
        } else {
            resourceTracker.registerResourceMissing(key, storyLocation, ResourceType.CHARACTER)
            eventTerminal.println(Severity.ERROR, "Resource not found: $key.")
            null
        }
    }

    companion object {
        /**
         * Get a background from a [color] with a specified [width] and [height].
         */
        fun getImageFromColor(color: Color, width: Int, height: Int): BufferedImage {
            val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            val g = image.createGraphics()
            g.color = color
            g.fillRect(0, 0, width, height)
            g.dispose()
            return image
        }

        /**
         * Get a default image for a missing character with a specified [color], [width] and [height].
         */
        fun getMissingCharacterResourceImage(color: Color, width: Int, height: Int): BufferedImage {
            return getImageFromColor(
                color,
                width,
                height
            )
        }

        /**
         * Scale an [image] by a specified [scaleFactor].
         */
        fun scaleImage(image: Image, scaleFactor: Double): BufferedImage {
            val originalWidth = image.getWidth(null)
            val originalHeight = image.getHeight(null)
            val newWidth = (originalWidth * scaleFactor).toInt()
            val newHeight = (originalHeight * scaleFactor).toInt()
            val bufferedImage = BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB)
            val graphics2D = bufferedImage.createGraphics()
            val transform = AffineTransform()

            transform.scale(scaleFactor, scaleFactor)

            graphics2D.transform(transform)
            graphics2D.drawImage(image, 0, 0, null)
            graphics2D.dispose()

            return bufferedImage
        }
    }
}
